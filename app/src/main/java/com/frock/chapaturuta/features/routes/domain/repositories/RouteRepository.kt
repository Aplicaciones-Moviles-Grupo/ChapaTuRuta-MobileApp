package com.frock.chapaturuta.features.routes.domain.repositories

import com.frock.chapaturuta.features.routes.domain.models.Route
import com.frock.chapaturuta.features.routes.domain.models.StopRoute

interface RouteRepository {

    suspend fun createRoute(name:String, price:Double, duration:String, distance:String, state:String, polylineRoute:String, driverId:Int): Route

    suspend fun createStopRoute(routeId: Int, stopId: Int): StopRoute

    suspend fun getRoutesByDriverId(driverId: Int): List<Route>

    suspend fun getStopRoutesByRouteId(routeId: Int): List<StopRoute>

    suspend fun deleteRoute(routeId: Int): Unit

    suspend fun deleteStopRoute(routeId: Int, stopRouteId: Int): Unit

    suspend fun activeRoute(routeId: Int): Route

    suspend fun inactiveRoute(routeId: Int): Route
}