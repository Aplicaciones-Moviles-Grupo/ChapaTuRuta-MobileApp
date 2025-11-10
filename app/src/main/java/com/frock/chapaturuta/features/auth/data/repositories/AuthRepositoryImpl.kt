package com.frock.chapaturuta.features.auth.data.repositories

import com.frock.chapaturuta.features.auth.data.remote.models.SignInRequestDto
import com.frock.chapaturuta.features.auth.data.remote.models.SignUpRequestDto
import com.frock.chapaturuta.features.auth.data.remote.services.AuthService
import com.frock.chapaturuta.features.auth.domain.models.User
import com.frock.chapaturuta.features.auth.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.let

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService): AuthRepository {

    override suspend fun login(email: String, password: String): User =
        withContext(Dispatchers.IO) {
            try {
                val response = authService.SignIn(SignInRequestDto(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { singInResponseDto ->
                        return@withContext User(
                            singInResponseDto.id,
                            singInResponseDto.email,
                            singInResponseDto.token
                        )
                    } ?: throw Exception("Response is empty")
                } else {
                    throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                throw Exception("Error al iniciar sesión: ${e.message}")
            }
        }

    override suspend fun register(email: String, password: String): User =
        withContext(Dispatchers.IO) {
            try {
                val response = authService.SignUp(SignUpRequestDto(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { signUpResponseDto ->
                        return@withContext User(
                            signUpResponseDto.id,
                            signUpResponseDto.email,
                            signUpResponseDto.token
                        )
                    } ?: throw Exception("Response is empty")
                } else {
                    throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                throw Exception("Error durante el registro: ${e.message}")
            }
        }
}