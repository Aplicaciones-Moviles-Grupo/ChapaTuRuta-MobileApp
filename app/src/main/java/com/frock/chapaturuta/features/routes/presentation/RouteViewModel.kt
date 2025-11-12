package com.frock.chapaturuta.features.routes.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frock.chapaturuta.features.routes.domain.models.DirectionsResult
import com.frock.chapaturuta.features.routes.domain.models.Route
import com.frock.chapaturuta.features.routes.domain.models.StopRoute
import com.frock.chapaturuta.features.routes.domain.repositories.DirectionsRepository
import com.frock.chapaturuta.features.routes.domain.repositories.RouteRepository
import com.frock.chapaturuta.features.stops.domain.models.Stop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    private val directionsRepository: DirectionsRepository): ViewModel() {

    private val _routes = MutableStateFlow<List<Route>>(emptyList())

    val routes: MutableStateFlow<List<Route>> = _routes

    private val _stopRoutes = MutableStateFlow<List<StopRoute>>(emptyList())

    val stopRoutes: MutableStateFlow<List<StopRoute>> = _stopRoutes

    private val _route = MutableStateFlow<Route?>(null)
    val route: MutableStateFlow<Route?> = _route

    private val _stopRoute = MutableStateFlow<StopRoute?>(null)
    val stopRoute: MutableStateFlow<StopRoute?> = _stopRoute

    private val _directionsResult = MutableStateFlow<DirectionsResult?>(null)
    val directionsResult: MutableStateFlow<DirectionsResult?> = _directionsResult


    fun getDirections( stops: List<Stop>){
        viewModelScope.launch {
            val result = directionsRepository.getDirectionsResult(stops)
            _directionsResult.value = result
        }
    }

    fun getAllRoutes(driverId:Int){
        viewModelScope.launch {
            _routes.value = routeRepository.getRoutesByDriverId(driverId)
        }
    }

    fun getStopRoutesByRouteId(routeId:Int){
        viewModelScope.launch {
            try {
                val result = routeRepository.getStopRoutesByRouteId(routeId)
                Log.d("RouteViewModel", "✅ Stops received: ${result.size}")
                _stopRoutes.value = result
            } catch (e: Exception) {
                Log.e("RouteViewModel", "❌ Error getting stops", e)
            }
            //_stopRoutes.value = routeRepository.getStopRoutesByRouteId(routeId)
        }
    }

    fun createRoute(name:String,price:Double, duration:String, distance:String, state:String, polylineRoute:String, driverId:Int){
        viewModelScope.launch {
            val route = routeRepository.createRoute(name,price, duration, distance, state, polylineRoute,driverId)
            _route.value = route

            _routes.value.toMutableList().apply {
                add(route)
                _routes.value = this
            }
        }
    }

    fun createStopRoute(routeId:Int, stopId:Int){
        viewModelScope.launch {
            val stopRoute = routeRepository.createStopRoute(routeId, stopId)
            _stopRoute.value = stopRoute

            _stopRoutes.value.toMutableList().apply {
                add(stopRoute)
                _stopRoutes.value = this
            }
        }
    }

    fun deleteRoute(routeId:Int){
        viewModelScope.launch {
            routeRepository.deleteRoute(routeId)
            _routes.value = _routes.value.filter { it.id != routeId }
        }
    }

    fun deleteStopRoute(routeId:Int,stopRouteId:Int){
        viewModelScope.launch {
            routeRepository.deleteStopRoute(routeId, stopRouteId)
            _stopRoutes.value = _stopRoutes.value.filter { it.id != stopRouteId }
        }
    }

    fun activeRoute(routeId:Int){
        viewModelScope.launch {
            _route.value = routeRepository.activeRoute(routeId)

            _routes.value = _routes.value.map {
                if (it.id == routeId) {
                    _route.value!!
                } else {
                    it
                }
            }
        }
    }

    fun inactiveRoute(routeId:Int){
        viewModelScope.launch {
            _route.value = routeRepository.inactiveRoute(routeId)

            _routes.value = _routes.value.map {
                if (it.id == routeId) {
                    _route.value!!
                } else {
                    it
                }
            }
        }
    }
}