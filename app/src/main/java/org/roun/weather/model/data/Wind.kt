package org.roun.weather.model.data

import com.squareup.moshi.Json

data class Wind(
        val speed: Float = 0F,
        @Json(name = "deg") val degrees: Float = 0F)
