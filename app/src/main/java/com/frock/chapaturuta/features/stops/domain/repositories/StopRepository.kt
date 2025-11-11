package com.frock.chapaturuta.features.stops.domain.repositories

import android.net.Uri
import com.frock.chapaturuta.features.stops.domain.models.Stop

interface StopRepository {
    suspend fun createStop(stopImageUri: Uri?, name: String, address: String, latitude: Double, longitude: Double, driverId: Int): Stop

    suspend fun updateStop(id: Int, stopImageUri: Uri?, name: String, address: String, latitude: Double, longitude: Double): Stop

    suspend fun getStopById(id: Int): Stop

    suspend fun getStopsByDriverId(driverId: Int): List<Stop>

    suspend fun deleteStop(id: Int): Unit
}