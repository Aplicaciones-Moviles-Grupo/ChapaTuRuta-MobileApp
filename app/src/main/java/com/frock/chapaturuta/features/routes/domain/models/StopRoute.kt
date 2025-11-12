package com.frock.chapaturuta.features.routes.domain.models

import com.frock.chapaturuta.features.stops.domain.models.Stop

data class StopRoute(
    val id:Int,
    val routeId:Int,
    val stop: Stop
)
