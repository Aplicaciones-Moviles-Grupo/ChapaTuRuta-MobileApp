package com.frock.chapaturuta.features.routes.domain.repositories

import com.frock.chapaturuta.features.routes.domain.models.DirectionsResult
import com.frock.chapaturuta.features.routes.domain.models.StopRoute
import com.frock.chapaturuta.features.stops.domain.models.Stop

interface DirectionsRepository {
    suspend fun getDirectionsResult(stops: List<Stop>): DirectionsResult?
}