package com.frock.chapaturuta.features.routes.data.remote.services

import com.frock.chapaturuta.features.profile.data.remote.models.ProfileDto
import com.frock.chapaturuta.features.routes.data.remote.models.CreateRouteRequest
import com.frock.chapaturuta.features.routes.data.remote.models.CreateStopRouteRequest
import com.frock.chapaturuta.features.routes.data.remote.models.RouteDto
import com.frock.chapaturuta.features.routes.data.remote.models.StopRouteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RouteService {

    @GET("drivers/{driverId}/routes")
    suspend fun getRoutesByDriverId(
        @Path("driverId") driverId:Int
    ): Response<List<RouteDto>>

    @GET("routes/{routeId}/stops")
    suspend fun getStopRoutesByRouteId(
        @Path("routeId") routeId:Int
    ): Response<List<StopRouteDto>>

    @POST("routes")
    suspend fun createRoute(@Body request: CreateRouteRequest):Response<RouteDto>

    @POST("routes/{routeId}/active")
    suspend fun activeRoute(@Path("routeId") routeId:Int):Response<RouteDto>

    @POST("routes/{routeId}/inactive")
    suspend fun inactiveRoute(@Path("routeId") routeId:Int):Response<RouteDto>

    @POST("routes/{routeId}/stops")
    suspend fun createStopRoute(
        @Path("routeId") routeId:Int,
        @Body request: CreateStopRouteRequest):Response<StopRouteDto>

    @DELETE("routes/{id}")
    suspend fun deleteRoute(@Path("id") id:Int): Response<Unit>

    @DELETE("routes/{routeId}/stops/{id}")
    suspend fun deleteStopRoute(@Path("routeId") routeId:Int, @Path("id") id:Int): Response<Unit>


}