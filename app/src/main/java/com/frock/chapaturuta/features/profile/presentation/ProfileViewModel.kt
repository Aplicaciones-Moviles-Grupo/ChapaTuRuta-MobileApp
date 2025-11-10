package com.frock.chapaturuta.features.profile.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frock.chapaturuta.features.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): ViewModel(){

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)

    private val _vehicleUiState = MutableStateFlow<VehicleUiState>(VehicleUiState.Initial)

    val uiState: StateFlow<ProfileUiState> = _uiState

    val vehicleUiState: StateFlow<VehicleUiState> = _vehicleUiState

    fun getProfileByUserId(userId:Int){
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val profile = profileRepository.getProfileByUserId(userId)
                _uiState.value = ProfileUiState.Success(profile)
            }catch (e:Exception){
                _uiState.value = ProfileUiState.Error(
                    e.message?:"Error desconocido al registrar perfil"
                )
            }
        }
    }

    fun getProfileById(id:Int){
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val profile = profileRepository.getProfile(id)
                _uiState.value = ProfileUiState.Success(profile)
            }catch (e:Exception){
                _uiState.value = ProfileUiState.Error(
                    e.message?:"Error desconocido al registrar perfil"
                )
            }
        }
    }

    fun updateProfile(id:Int,
                      profileImageUri: Uri?,
                      firstName: String,
                      lastName: String,
                      email: String,
                      phoneNumber: String,
                      profileType: String){
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val profile = profileRepository.updateProfile(id,profileImageUri,firstName,lastName,email,phoneNumber,profileType)
                _uiState.value = ProfileUiState.Success(profile)
            }catch (e:Exception){
                _uiState.value = ProfileUiState.Error(
                    e.message?:"Error desconocido al actualizar perfil"
                )
            }
        }
    }

    fun createVehicle(vehicleImageUri:Uri?, plate:String, model:String, color:String, profileId:Int){
        viewModelScope.launch {

            Log.d("ProfileViewModel", "createVehicle() llamado con profileId = $profileId")

            _vehicleUiState.value = VehicleUiState.Loading
            try {
                val vehicle = profileRepository.createVehicle(vehicleImageUri,plate,model,color,profileId)
                _vehicleUiState.value = VehicleUiState.Success(vehicle)
            }catch (e:Exception){
                _vehicleUiState.value = VehicleUiState.Error(
                    e.message?:"Error desconocido al crear vehiculo"
                )
            }
        }
    }

    fun getVehicleByProfileId(profileId:Int){
        viewModelScope.launch {
            _vehicleUiState.value = VehicleUiState.Loading
            try {
                val vehicle = profileRepository.getVehicleByProfileId(profileId)
                _vehicleUiState.value = VehicleUiState.Success(vehicle)
            }catch (e:Exception){
                _vehicleUiState.value = VehicleUiState.Error(
                    e.message?:"Error desconocido al obtener vehiculo"
                )
            }
        }
    }

    fun updateVehicle(profileId:Int, id:Int, vehicleImageUri:Uri?, plate:String, model:String, color:String){
        viewModelScope.launch {
            _vehicleUiState.value = VehicleUiState.Loading
            try {
                val vehicle = profileRepository.updateVehicle(profileId,id,vehicleImageUri,plate,model,color)
                _vehicleUiState.value = VehicleUiState.Success(vehicle)
            }catch (e:Exception){
                _vehicleUiState.value = VehicleUiState.Error(
                    e.message?:"Error desconocido al actualizar vehiculo"
                )
            }
        }
    }

}