package com.frock.chapaturuta.features.routes.data.remote.models

data class RouteDto(
    val id:Int,
    val name:String,
    val price:Double,
    val duration:String,
    val distance:String,
    val state: String,
    val polylineRoute: String,
    val driverId:Int
)
