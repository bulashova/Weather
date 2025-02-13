package ru.weather.repository

import ru.weather.dto.City
import kotlinx.coroutines.flow.Flow
import ru.weather.api.ApiService
import ru.weather.dto.WeatherReport

interface WeatherRepository {

    val data: Flow<List<ru.weather.dto.List>>

    fun get(callback: ApiService.WeatherCallback<WeatherReport>)

    suspend fun fillDb(weather: List<ru.weather.dto.List>, city: City)
    suspend fun clear()

}