package org.roun.weather.ui

import android.arch.lifecycle.*
import org.roun.weather.WeatherApplication
import org.roun.weather.model.WeatherRepository
import org.roun.weather.model.data.WeatherData
import javax.inject.Inject
import org.roun.weather.model.Resource
import org.roun.weather.model.data.QueryHistory
import java.util.*

class WeatherViewModel: ViewModel() {

    val REFRESH_TIME_IN_SECONDS = 900

    @Inject
    lateinit var weatherRepository: WeatherRepository
    val weatherQuery: MutableLiveData<String> = MutableLiveData()
    val weatherData: LiveData<Resource<WeatherData>> = Transformations.switchMap(weatherQuery) { query -> weatherRepository.getWeatherData(query) };

    init {
        WeatherApplication.weatherComponent.inject(this)
    }

    fun setQuery(query: String) {
        weatherQuery.value = query
    }

    fun refresh(lifecycleOwner: LifecycleOwner) {
        val queryHistoryLiveData = weatherRepository.getLatestQueryHistory()
        queryHistoryLiveData.observe(lifecycleOwner, Observer { queryHistory: QueryHistory? ->
            queryHistoryLiveData.removeObservers(lifecycleOwner)

            if (queryHistory != null && (Date().after(Date(queryHistory.queryTime.time + REFRESH_TIME_IN_SECONDS * 1000)) || weatherQuery.value == null)) {
                setQuery(queryHistory.queryText)
            }
        })
    }
}
