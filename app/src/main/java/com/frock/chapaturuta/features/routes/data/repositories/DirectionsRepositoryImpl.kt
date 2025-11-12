package com.frock.chapaturuta.features.routes.data.repositories

import android.util.Log
import com.frock.chapaturuta.features.routes.data.remote.services.DirectionsService
import com.frock.chapaturuta.features.routes.domain.models.DirectionsResult
import com.frock.chapaturuta.features.routes.domain.models.StopRoute
import com.frock.chapaturuta.features.routes.domain.repositories.DirectionsRepository
import com.frock.chapaturuta.features.stops.domain.models.Stop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DirectionsRepositoryImpl @Inject constructor(private val directionsService: DirectionsService):
    DirectionsRepository {

    override suspend fun getDirectionsResult(stops: List<Stop>): DirectionsResult? = withContext(
        Dispatchers.IO) {
        try {
            val origin = "${stops.first().latitude},${stops.first().longitude}"
            val destination = "${stops.last().latitude},${stops.last().longitude}"
            val waypoints = if (stops.size > 2) {
                stops.subList(1, stops.size - 1)
                    .joinToString("|") { "${it.latitude},${it.longitude}" }
            } else null

            Log.d("DirectionsRepo", "📍 Origin: $origin")
            Log.d("DirectionsRepo", "🏁 Destination: $destination")
            Log.d("DirectionsRepo", "🗺️ Waypoints: $waypoints")

            val response = directionsService.getDirections(
                origin = origin,
                destination = destination,
                waypoints = waypoints
            )

            Log.d("DirectionsRepo", "📦 HTTP Response Code: ${response.code()}")
            Log.d("DirectionsRepo", "✅ Successful: ${response.isSuccessful}")

            if(response.isSuccessful){
                response.body()?.let { directionsResponse ->

                    Log.d("DirectionsRepo", "🌐 Directions response body: $directionsResponse")
                    val route = directionsResponse.routes.firstOrNull()

                    val totalDistance = route?.legs?.sumOf{ it.distance.value } ?: 0
                    val totalDuration = route?.legs?.sumOf{ it.duration.value } ?: 0

                    val totalDistanceText = "${totalDistance / 1000} km"
                    val totalDurationText = "${totalDuration / 60} mins"

                    Log.d("DirectionsRepo", "📏 Total Distance: $totalDistanceText")
                    Log.d("DirectionsRepo", "⏱️ Total Duration: $totalDurationText")
                    Log.d("DirectionsRepo", "🧩 Polyline: ${route?.overviewPolyline?.points?.take(50)}...")

                    return@withContext DirectionsResult(
                        polylinePoints = route?.overviewPolyline?.points ?: "",
                        distance = totalDistanceText,
                        duration = totalDurationText
                    )
                }
            }
        }catch (e:Exception){
            throw Exception("Error obtaining directions: ${e.message}")
        }
        return@withContext null
    }
}