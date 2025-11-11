package com.frock.chapaturuta.features.stops.data.remote.models

import com.google.gson.annotations.SerializedName

data class GeocodingResponseDto(
    @SerializedName("results")
    val results: List<GeocodingResult>,
    @SerializedName("status")
    val status: String
)

data class GeocodingResult(
    @SerializedName("formatted_address")
    val formattedAddress: String,
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("types")
    val types: List<String>
)

data class Geometry(
    @SerializedName("location")
    val location: Location,
    @SerializedName("location_type")
    val locationType: String
)

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)
