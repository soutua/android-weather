package org.roun.weather.dagger

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import org.roun.weather.database.AppDatabase
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

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "db").build()
    }
}
