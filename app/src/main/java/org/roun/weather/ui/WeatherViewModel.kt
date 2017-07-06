package org.roun.weather.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.roun.weather.WeatherApplication
import org.roun.weather.model.WeatherRepository
import org.roun.weather.model.data.WeatherData
import javax.inject.Inject
import android.arch.lifecycle.Transformations
import org.roun.weather.model.Resource

class WeatherViewModel: ViewModel() {

    init {
        WeatherApplication.weatherComponent.inject(this)
    }

    @Inject
    lateinit var weatherRepository: WeatherRepository
    val weatherQuery: MutableLiveData<String> = MutableLiveData()
    val weatherData: LiveData<Resource<WeatherData>> = Transformations.switchMap(weatherQuery) { query -> weatherRepository.getWeatherData(query) };

    fun setQuery(query: String) {
        weatherQuery.value = query
    }
}
