package org.roun.weather.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.roun.weather.WeatherApplication
import org.roun.weather.database.AppDatabase
import org.roun.weather.model.data.QueryHistory
import org.roun.weather.model.data.WeatherData
import org.roun.weather.network.WeatherAPI
import java.util.*
import javax.inject.Inject

class WeatherRepository(val weatherAPI: WeatherAPI) {

    @Inject
    lateinit var db: AppDatabase

    init {
        WeatherApplication.weatherComponent.inject(this)
    }

    fun getWeatherData(query: String): LiveData<Resource<WeatherData>> {
        val queryLower = query.toLowerCase()
        return LiveDataReactiveStreams.fromPublisher(weatherAPI.getWeatherData(queryLower, "metric")
                .subscribeOn(Schedulers.io())
                .doOnComplete { db.getQueryHistoryDao().save(QueryHistory(queryLower, Date())) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { weatherData -> Resource.success(weatherData) }
                .onErrorReturn { error -> Resource.error(error.message) })
    }

    fun getLatestQueryHistory(): LiveData<QueryHistory> {
        return db.getQueryHistoryDao().getLatestQueryHistory()
    }
}
