package ru.weather.repository

import ru.weather.dto.City
import kotlinx.coroutines.flow.Flow
import ru.weather.api.ApiService
import ru.weather.dto.CitySearchResult
import ru.weather.dto.WeatherReport

interface WeatherRepository {

    val data: Flow<List<ru.weather.dto.List>>
    val cityData: Flow<List<City>>

    fun get(lat: Double, lon: Double, callback: ApiService.WeatherCallback<WeatherReport>)
    fun getCoordinates(city: String, callback: ApiService.WeatherCallback<List<CitySearchResult>>)

    suspend fun fillDb(weather: List<ru.weather.dto.List>, city: City)
    suspend fun clear()
}