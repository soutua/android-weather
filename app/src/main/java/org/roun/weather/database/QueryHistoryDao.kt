package org.roun.weather.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.roun.weather.model.data.QueryHistory

@Dao
interface QueryHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(queryHistory: QueryHistory)

    @Query("SELECT * FROM QueryHistory WHERE queryTime = (SELECT MAX(queryTime) FROM QueryHistory)")
    fun getLatestQueryHistory(): LiveData<QueryHistory>

    @Query("SELECT * FROM QueryHistory ORDER BY queryTime DESC")
    fun getAllQueryHistory(): LiveData<List<QueryHistory>>
}
