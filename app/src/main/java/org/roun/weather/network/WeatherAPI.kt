package org.roun.weather.network

import io.reactivex.Flowable
import org.roun.weather.model.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather")
    fun getWeatherData(@Query("q") query: String, @Query("units") units: String): Flowable<WeatherData>
}