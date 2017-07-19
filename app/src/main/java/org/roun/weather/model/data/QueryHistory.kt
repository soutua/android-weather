package org.roun.weather.model.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class QueryHistory(@PrimaryKey val queryText: String, val queryTime: Date)
