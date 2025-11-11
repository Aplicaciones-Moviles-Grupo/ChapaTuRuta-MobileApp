package com.frock.chapaturuta.features.stops.data.remote.services

import com.frock.chapaturuta.features.stops.data.remote.models.GeocodingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("maps/api/geocode/json")
    suspend fun getAddressFromLatLng(
        @Query("latlng") latlng: String,
        @Query("key") apiKey: String = "AIzaSyDg-lgll2qxnJ7aHcKQY5aBH5veN3kuRPY"
    ): Response<GeocodingResponseDto>
}