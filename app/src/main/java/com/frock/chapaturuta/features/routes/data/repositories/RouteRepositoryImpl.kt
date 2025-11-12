package com.frock.chapaturuta.features.routes.data.repositories

import com.frock.chapaturuta.features.routes.data.remote.models.CreateRouteRequest
import com.frock.chapaturuta.features.routes.data.remote.models.CreateStopRouteRequest
import com.frock.chapaturuta.features.routes.data.remote.services.RouteService
import com.frock.chapaturuta.features.routes.domain.models.Route
import com.frock.chapaturuta.features.routes.domain.models.StopRoute
import com.frock.chapaturuta.features.routes.domain.repositories.RouteRepository
import com.frock.chapaturuta.features.stops.domain.models.Stop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(private val routeService: RouteService): RouteRepository{


    override suspend fun createRoute(
        name: String,
        price: Double,
        duration: String,
        distance: String,
        state: String,
        polylineRoute:String,
        driverId: Int
    ): Route = withContext(Dispatchers.IO) {
        try {
            val request = CreateRouteRequest(name,price,duration,distance,state,polylineRoute,driverId)
            val response = routeService.createRoute(request)
            if(response.isSuccessful){
                response.body()?.let { routeDto ->
                    Route(
                        routeDto.id,
                        routeDto.name,
                        routeDto.price,
                        routeDto.duration,
                        routeDto.distance,
                        routeDto.state,
                        routeDto.polylineRoute,
                        routeDto.driverId
                    )
                }?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error creating route: ${e.message}")
        }
    }

    override suspend fun deleteRoute(routeId: Int)=withContext(Dispatchers.IO) {
        try {
            val response = routeService.deleteRoute(routeId)
            if(!response.isSuccessful){
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error deleting route: ${e.message}")
        }
    }

    override suspend fun createStopRoute(routeId: Int, stopId: Int): StopRoute {
        return withContext(Dispatchers.IO) {
            try {
                val request = CreateStopRouteRequest(stopId)
                val response = routeService.createStopRoute(routeId, request)
                if(response.isSuccessful){
                    response.body()?.let { stopRouteDto ->
                        val stop = Stop(
                            stopRouteDto.stop.id,
                            stopRouteDto.stop.name,
                            stopRouteDto.stop.address,
                            stopRouteDto.stop.latitude,
                            stopRouteDto.stop.longitude,
                            stopRouteDto.stop.stopImageUrl,
                            stopRouteDto.stop.stopImagePublicId,
                            stopRouteDto.stop.driverId
                        )
                        StopRoute(
                            stopRouteDto.id,
                            stopRouteDto.routeId,
                            stop
                        )
                    }?: throw Exception("Response is empty")
                }else{
                    throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
                }
            }catch (e:Exception){
                throw Exception("Error creating stop route: ${e.message}")
            }
        }
    }

    override suspend fun deleteStopRoute(routeId: Int, stopRouteId: Int) {
        try {
            val response = routeService.deleteStopRoute(routeId, stopRouteId)
            if(!response.isSuccessful){
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error deleting stop route: ${e.message}")
        }
    }

    override suspend fun getRoutesByDriverId(driverId: Int): List<Route> = withContext(Dispatchers.IO) {
        try {
            val response = routeService.getRoutesByDriverId(driverId)
            if(response.isSuccessful){
                response.body()?.let { routesDto ->
                    routesDto.map { routeDto->
                        Route(
                            routeDto.id,
                            routeDto.name,
                            routeDto.price,
                            routeDto.duration,
                            routeDto.distance,
                            routeDto.state,
                            routeDto.polylineRoute,
                            routeDto.driverId
                        )
                    }
                }?: throw Exception("Response is empty")
            } else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error getting routes by driver id: ${e.message}")
        }
    }

    override suspend fun getStopRoutesByRouteId(routeId: Int): List<StopRoute> = withContext(
        Dispatchers.IO) {
        try {
            val response = routeService.getStopRoutesByRouteId(routeId)
            if(response.isSuccessful){
                response.body()?.let { stopRoutesDto ->
                    stopRoutesDto.map { stopRouteDto->
                        val stop = Stop(
                            stopRouteDto.stop.id,
                            stopRouteDto.stop.name,
                            stopRouteDto.stop.address,
                            stopRouteDto.stop.latitude,
                            stopRouteDto.stop.longitude,
                            stopRouteDto.stop.stopImageUrl,
                            stopRouteDto.stop.stopImagePublicId,
                            stopRouteDto.stop.driverId
                        )
                        StopRoute(
                            stopRouteDto.id,
                            stopRouteDto.routeId,
                            stop
                        )
                    }
                }?: throw Exception("Response is empty")
            } else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }

        }catch (e:Exception){
            throw Exception("Error getting stop routes by route id: ${e.message}")
        }

    }

    override suspend fun activeRoute(routeId: Int): Route = withContext(Dispatchers.IO) {
        try {
            val response = routeService.activeRoute(routeId)
            if(response.isSuccessful) {
                response.body()?.let { routeDto ->
                    Route(
                        routeDto.id,
                        routeDto.name,
                        routeDto.price,
                        routeDto.duration,
                        routeDto.distance,
                        routeDto.state,
                        routeDto.polylineRoute,
                        routeDto.driverId
                    )
                } ?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error activating route: ${e.message}")
        }
    }

    override suspend fun inactiveRoute(routeId: Int): Route=withContext(Dispatchers.IO) {
        try {
            val response = routeService.inactiveRoute(routeId)
            if(response.isSuccessful) {
                response.body()?.let { routeDto ->
                    Route(
                        routeDto.id,
                        routeDto.name,
                        routeDto.price,
                        routeDto.duration,
                        routeDto.distance,
                        routeDto.state,
                        routeDto.polylineRoute,
                        routeDto.driverId
                    )
                } ?: throw Exception("Response is empty")
            }else{
                throw Exception("Error HTTP: ${response.code()} - ${response.message()}")
            }
        }catch (e:Exception){
            throw Exception("Error deactivating route: ${e.message}")
        }
    }
}