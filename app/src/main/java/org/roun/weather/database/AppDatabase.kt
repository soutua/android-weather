package org.roun.weather.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import org.roun.weather.model.data.QueryHistory

@Database(entities = arrayOf(QueryHistory::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getQueryHistoryDao(): QueryHistoryDao
}
