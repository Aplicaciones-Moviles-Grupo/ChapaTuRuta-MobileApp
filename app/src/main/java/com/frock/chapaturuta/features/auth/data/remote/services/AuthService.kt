package com.frock.chapaturuta.features.auth.data.remote.services

import com.frock.chapaturuta.features.auth.data.remote.models.SignInRequestDto
import com.frock.chapaturuta.features.auth.data.remote.models.SignUpRequestDto
import com.frock.chapaturuta.features.auth.data.remote.models.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/sign-in")
    suspend fun SignIn(@Body request: SignInRequestDto): Response<UserDto>

    @POST("auth/sign-up")
    suspend fun SignUp(@Body requestDto: SignUpRequestDto): Response<UserDto>
}