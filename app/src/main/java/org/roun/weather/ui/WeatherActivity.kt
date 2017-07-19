package org.roun.weather.ui

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.format.DateUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather.*
import org.roun.weather.R
import org.roun.weather.model.Resource
import org.roun.weather.model.data.WeatherData

class WeatherActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)
        val weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        weatherViewModel.weatherData.observe(this, Observer { resource ->
            if (resource != null && resource.status == Resource.Status.SUCCESS) {
                updateUI(resource.data)
            } else if (resource != null && resource.status == Resource.Status.ERROR) {
                handleError(resource.message)
            }
        })

        query_text.setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                weatherViewModel.setQuery(query_text.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        weatherViewModel.refresh(this)
    }

    private fun updateUI(weatherData: WeatherData?) {
        if (weatherData != null) {
            if (!weatherData.weatherDescriptions.isEmpty()) {
                val weatherDescription = weatherData.weatherDescriptions.get(0)
                Picasso.with(this).load(weatherDescription.getIconUrl()).into(weather_image);
            }

            location_value.text = weatherData.name
            temperature_value.text = weatherData.weatherMain.temperature.toString()
            wind_speed_value.text = getString(R.string.wind_speed_value, weatherData.wind.speed)
            temperature_value.text = getString(R.string.temperature_value, weatherData.weatherMain.temperature)
            observation_time_value.text = DateUtils.formatDateTime(this, weatherData.date.time, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME)
        }
    }

    private fun handleError(message: String?) {
        if (message != null) {

            if (message == "HTTP 404 Not Found") {
                Toast.makeText(this, R.string.error_location_not_found, Toast.LENGTH_LONG).show()
            }
        }
    }
}
