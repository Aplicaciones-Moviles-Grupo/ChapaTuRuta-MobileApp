package com.frock.chapaturuta.features.routes.data.remote.models

import com.frock.chapaturuta.features.stops.data.remote.models.StopDto

data class StopRouteDto(
    val id:Int,
    val routeId:Int,
    val stop: StopDto
)
