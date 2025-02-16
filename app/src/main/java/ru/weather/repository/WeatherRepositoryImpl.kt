package ru.weather.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.weather.api.ApiService
import ru.weather.dao.CityDao
import ru.weather.dao.WeatherDao
import ru.weather.dto.City
import ru.weather.dto.CitySearchResult
import ru.weather.dto.WeatherReport
import ru.weather.entity.CityEntity.Companion.fromDto
import ru.weather.entity.ListEntity
import ru.weather.entity.toDto
import ru.weather.entity.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val dao: WeatherDao,
    private val apiService: ApiService

) : WeatherRepository {

    override val data: Flow<List<ru.weather.dto.List>> = dao.getAll()
        .map(List<ListEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override fun get(callback: ApiService.WeatherCallback<WeatherReport>) {
        apiService.get(callback)
    }

    override fun getCoordinates(city: String, callback: ApiService.WeatherCallback<List<CitySearchResult>>) {
        apiService.getCoordinates(city, callback)
    }

    override suspend fun fillDb(weather: List<ru.weather.dto.List>, city: City) {
        dao.insert(weather.toEntity())
        cityDao.insert(fromDto(city))
    }

    override suspend fun clear() {
        dao.clear()
    }
}