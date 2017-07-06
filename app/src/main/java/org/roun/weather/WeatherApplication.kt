package org.roun.weather

import android.app.Application
import org.roun.weather.dagger.AppModule
import org.roun.weather.dagger.DaggerWeatherComponent
import org.roun.weather.dagger.WeatherComponent

class WeatherApplication: Application() {

    companion object {
        lateinit var weatherComponent: WeatherComponent
    }

    init {
        weatherComponent = DaggerWeatherComponent.builder().appModule(AppModule(this)).build()
    }

}
