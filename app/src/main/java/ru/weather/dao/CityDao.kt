package ru.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.weather.entity.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM CityEntity ORDER BY id DESC")
    fun getCity(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cities: List<CityEntity>)

    @Query("DELETE FROM CityEntity")
    suspend fun clear()
}