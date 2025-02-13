package ru.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.weather.dao.CityDao
import ru.weather.dao.WeatherDao
import ru.weather.entity.CityEntity
import ru.weather.entity.ListEntity

@Database(entities = [ListEntity::class, CityEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
}