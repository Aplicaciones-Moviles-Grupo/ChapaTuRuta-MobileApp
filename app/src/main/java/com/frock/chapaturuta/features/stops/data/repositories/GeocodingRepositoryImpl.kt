package com.frock.chapaturuta.features.stops.data.repositories

import com.frock.chapaturuta.features.stops.data.remote.services.GeocodingService
import com.frock.chapaturuta.features.stops.domain.repositories.GeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(private val geocodingService: GeocodingService) : GeocodingRepository {
    override suspend fun getAddressFromLatLng(latitude: Double, longitude: Double): String =
        withContext(Dispatchers.IO){
            try{
                val latLng = "$latitude,$longitude"
                val response = geocodingService.getAddressFromLatLng(latLng)
                if(response.isSuccessful){

                    //Return this
                    response.body()?.results?.firstOrNull()?.formattedAddress?:""
                    }else{
                        throw Exception("Response is empty")
                    }
            }catch (e:Exception){
                throw Exception("Error al obtener la dirección: ${e.message}")
            }
        }
}