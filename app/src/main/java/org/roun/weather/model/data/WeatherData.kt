package org.roun.weather.model.data

import com.squareup.moshi.Json
import java.util.*

data class WeatherData(
        @Json(name = "coord") val coordinates: Coordinates,
        @Json(name = "weather") val weatherDescriptions: List<WeatherDescription>,
        @Json(name = "main") val weatherMain: WeatherMain,
        val visibility: Float = 0F,
        val wind: Wind,
        @Json(name = "dt") val date: Date,
        val id: Long,
        val name: String)
