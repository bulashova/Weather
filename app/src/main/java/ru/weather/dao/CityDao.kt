package ru.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.weather.entity.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reports: List<CityEntity>)

    @Query("DELETE FROM CityEntity")
    suspend fun clear()
}