package ru.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.weather.entity.ListEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM ListEntity ORDER BY id DESC")
    fun getAll(): Flow<List<ListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: ListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: List<ListEntity>)

    @Query("DELETE FROM ListEntity")
    suspend fun clear()
}