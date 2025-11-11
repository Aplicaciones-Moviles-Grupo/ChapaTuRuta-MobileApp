package com.frock.chapaturuta.features.stops.data.remote.services

import com.frock.chapaturuta.features.stops.data.remote.models.StopDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface StopService {

    @Multipart
    @POST("stops")
    suspend fun createStop(
        @Part stopImage: MultipartBody.Part?,
        @Part("Name") name: RequestBody,
        @Part("Address") address: RequestBody,
        @Part("Latitude") latitude: RequestBody,
        @Part("Longitude") longitude: RequestBody,
        @Part("DriverId") driverId: RequestBody
    ): Response<StopDto>

    @Multipart
    @PUT("stops/{id}")
    suspend fun updateStop(
        @Path("id") id: Int,
        @Part stopImage: MultipartBody.Part?,
        @Part("Name") name: RequestBody,
        @Part("Address") address: RequestBody,
        @Part("Latitude") latitude: RequestBody,
        @Part("Longitude") longitude: RequestBody
    ): Response<StopDto>

    @GET("stops/{id}")
    suspend fun getStopById(
        @Path("id") id: Int
    ): Response<StopDto>

    @GET("stops")
    suspend fun getAllStopsByDriverId(
        @Query("driverId") driverId: Int
    ): Response<List<StopDto>>

    @DELETE("stops/{id}")
    suspend fun deleteStop(
        @Path("id") id: Int): Response<Unit>

}