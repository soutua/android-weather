package org.roun.weather.model.data

import com.squareup.moshi.Json

data class Coordinates(
        @Json(name = "lon") val longitude: Float = 0F,
        @Json(name = "lat") val latitude: Float = 0F)
