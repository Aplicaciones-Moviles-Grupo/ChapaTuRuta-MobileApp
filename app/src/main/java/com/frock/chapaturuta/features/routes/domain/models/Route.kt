package com.frock.chapaturuta.features.routes.domain.models

data class Route(
    val id:Int,
    val name:String,
    val price:Double,
    val duration:String,
    val distance:String,
    val state: String,
    val polylineRoute:String,
    val driverId:Int
)
