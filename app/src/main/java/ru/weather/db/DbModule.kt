package ru.weather.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.weather.dao.CityDao
import ru.weather.dao.WeatherDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "appdb.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideWeatherDao(appDb: AppDb): WeatherDao = appDb.weatherDao()

    @Provides
    fun provideCityDao(appDb: AppDb): CityDao = appDb.cityDao()
}