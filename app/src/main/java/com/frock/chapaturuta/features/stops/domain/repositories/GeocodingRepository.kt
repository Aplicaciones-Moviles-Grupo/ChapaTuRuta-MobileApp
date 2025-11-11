package com.frock.chapaturuta.features.stops.domain.repositories

interface GeocodingRepository {
    suspend fun getAddressFromLatLng(latitude: Double, longitude: Double): String
}