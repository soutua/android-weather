package org.roun.weather.model.data

data class WeatherDescription(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String) {

    fun getIconUrl(): String {
        return "http://openweathermap.org/img/w/$icon.png"
    }
}
