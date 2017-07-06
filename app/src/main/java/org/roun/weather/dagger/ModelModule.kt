package org.roun.weather.dagger

import dagger.Module
import dagger.Provides
import org.roun.weather.model.WeatherRepository
import org.roun.weather.network.WeatherAPI
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepository(weatherAPI)
    }

}
