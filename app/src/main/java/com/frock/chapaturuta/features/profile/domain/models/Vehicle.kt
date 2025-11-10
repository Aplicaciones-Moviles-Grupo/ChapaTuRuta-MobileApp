package com.frock.chapaturuta.features.profile.domain.models

data class Vehicle(
    val id: Int,
    val plate:String,
    val model:String,
    val color:String,
    val vehicleImageUrl:String,
    val vehicleImagePublicId:String,
    val profileId:Int
)