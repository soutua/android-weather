package org.roun.weather.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.roun.weather.model.data.WeatherData
import org.roun.weather.network.WeatherAPI

class WeatherRepository(val weatherAPI: WeatherAPI) {

    fun getWeatherData(query: String): LiveData<Resource<WeatherData>> {
        return LiveDataReactiveStreams.fromPublisher(weatherAPI.getWeatherData(query, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { weatherData -> Resource.success(weatherData) }
                .onErrorReturn { error -> Resource.error(error.message) })
    }

}
