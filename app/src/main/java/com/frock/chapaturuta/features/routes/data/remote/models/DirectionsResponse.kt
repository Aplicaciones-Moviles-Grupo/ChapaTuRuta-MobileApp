package com.frock.chapaturuta.features.routes.data.remote.models

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @SerializedName("routes")
    val routes: List<DirectionsRoute>,
    @SerializedName("status")
    val status: String
)

data class DirectionsRoute(
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline,

    @SerializedName("legs")
    val legs: List<Leg>
)

data class OverviewPolyline(
    @SerializedName("points")
    val points: String
)

data class Leg(
    @SerializedName("distance")
    val distance: Distance,

    @SerializedName("duration")
    val duration: Duration,

    @SerializedName("start_address")
    val startAddress: String,

    @SerializedName("end_address")
    val endAddress: String,
)

data class Distance(
    @SerializedName("text")
    val text: String,

    @SerializedName("value")
    val value: Int
)
data class Duration(
    @SerializedName("text")
    val text: String,

    @SerializedName("value")
    val value: Int
)
