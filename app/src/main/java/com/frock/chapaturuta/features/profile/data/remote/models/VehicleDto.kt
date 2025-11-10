package com.frock.chapaturuta.features.profile.data.remote.models

data class VehicleDto(
    val id: Int,
    val plate:String,
    val model:String,
    val color:String,
    val vehicleImageUrl:String,
    val vehicleImagePublicId:String,
    val profileId:Int
)
