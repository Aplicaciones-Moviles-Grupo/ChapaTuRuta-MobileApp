package com.frock.chapaturuta.features.stops.data.remote.models

data class StopDto(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val stopImageUrl:String,
    val stopImagePublicId:String,
    val driverId: Int
)
