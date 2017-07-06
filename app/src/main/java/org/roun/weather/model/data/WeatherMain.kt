package org.roun.weather.model.data

import com.squareup.moshi.Json

data class WeatherMain(
        @Json(name = "temp") val temperature: Float = 0F,
        val pressure: Float = 0F,
        val humidity: Float = 0F,
        @Json(name = "temp_min") val minTemperature: Float = 0F,
        @Json(name = "temp_max") val maxTemperature: Float = 0F)
