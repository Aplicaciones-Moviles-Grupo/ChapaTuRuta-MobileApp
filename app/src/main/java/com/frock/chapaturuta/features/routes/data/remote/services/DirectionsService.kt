package com.frock.chapaturuta.features.routes.data.remote.services

import com.frock.chapaturuta.features.routes.data.remote.models.DirectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DirectionsService {

    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("waypoints") waypoints: String? = null,
        @Query("mode") mode: String = "driving",
        @Query("key") apiKey: String = "AIzaSyDg-lgll2qxnJ7aHcKQY5aBH5veN3kuRPY"
    ): Response<DirectionsResponse>
}