package com.frock.chapaturuta.features.stops.domain.models

data class Stop(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val stopImageUrl:String,
    val stopImagePublicId:String,
    val driverId: Int
)
