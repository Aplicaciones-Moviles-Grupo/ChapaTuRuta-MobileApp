package com.frock.chapaturuta.features.profile.data.remote.services

import com.frock.chapaturuta.features.profile.data.remote.models.ProfileDto
import com.frock.chapaturuta.features.profile.data.remote.models.VehicleDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileService {

    @GET("users/{userId}/profile")
    suspend fun getProfileByUserId(
        @Path("userId") userId:Int
    ): Response<ProfileDto>

    @GET("profiles/{id}")
    suspend fun getProfile(
        @Path("id") id:Int
    ): Response<ProfileDto>

    @Multipart
    @PUT("profiles/{id}")
    suspend fun updateProfile(
        @Path("id") id:Int,
        @Part profileImage: MultipartBody.Part?,
        @Part("FirstName") firstName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("Email") email: RequestBody,
        @Part("PhoneNumber") phoneNumber: RequestBody,
        @Part("ProfileType") profileType: RequestBody,
        ):Response<ProfileDto>

    @Multipart
    @POST("drivers/{profileId}/vehicle")
    suspend fun createVehicle(
        @Path("profileId") profileId:Int,
        @Part vehicleImage: MultipartBody.Part?,
        @Part("Plate") plate: RequestBody,
        @Part("Model") model:RequestBody,
        @Part("Color") color:RequestBody
    ): Response<VehicleDto>

    @GET("drivers/{profileId}/vehicle")
    suspend fun getVehicleByProfileId(@Path ("profileId") profileId:Int): Response<VehicleDto>

    @Multipart
    @PUT("drivers/{profileId}/vehicle/{id}")
    suspend fun updateVehicle(
        @Path("profileId") profileId:Int,
        @Path("id") id:Int,
        @Part vehicleImage: MultipartBody.Part?,
        @Part("Plate") plate: RequestBody,
        @Part("Model") model:RequestBody,
        @Part("Color") color:RequestBody
    ): Response<VehicleDto>
}