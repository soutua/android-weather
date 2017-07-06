package org.roun.weather.dagger

import dagger.Component
import org.roun.weather.ui.WeatherActivity
import org.roun.weather.ui.WeatherViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ModelModule::class, NetworkModule::class))
interface WeatherComponent {

    fun inject(weatherViewModel: WeatherViewModel)

}
