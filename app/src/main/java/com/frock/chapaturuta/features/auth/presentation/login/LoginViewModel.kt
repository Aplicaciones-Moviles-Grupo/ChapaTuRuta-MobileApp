package com.frock.chapaturuta.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frock.chapaturuta.core.data.datastore.AuthDataStore
import com.frock.chapaturuta.features.auth.domain.models.User
import com.frock.chapaturuta.features.auth.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore
): ViewModel(){
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email:String, password:String){
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = authRepository.login(email, password)

                // Guardamos el token usando DataStore
                authDataStore.saveToken(user.token)

                // Emitimos estado de éxito con el usuario obtenido
                _uiState.value = LoginUiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Error desconocido al iniciar sesión"
                )
            }
        }
    }

    fun register(email:String, password:String, repeatPassword: String){
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                if(password != repeatPassword){
                    throw Exception("Password doesn't match. Please try again.")
                }
                val user = authRepository.register(email,password)
                // Guardamos el token usando DataStore
                authDataStore.saveToken(user.token)

                // Emitimos estado de éxito con el usuario obtenido
                _uiState.value = LoginUiState.Success(user)
            } catch (e: Exception){
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Error desconocido registrar usuario"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Initial
    }

    /**
     * Verifica si ya existe un token guardado (por ejemplo, para saltar el login)
     */
    fun checkExistingSession() {
        viewModelScope.launch{
            val token = authDataStore.getToken()
            if (!token.isNullOrEmpty()) {
                val user = User(id = 0, email = "", token = token)
                _uiState.value = LoginUiState.Success(user)
            }
        }
    }
}