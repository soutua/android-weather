package org.roun.weather.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateJsonAdapter {

    @FromJson fun fromJson(json: String): Date {
        return Date(json.toLong() * 1000)
    }

    @ToJson fun toJson(date: Date): String {
        return (date.time / 1000).toString()
    }
}
