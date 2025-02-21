package ru.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.weather.api.ApiService
import ru.weather.dto.City
import ru.weather.dto.CitySearchResult
import ru.weather.dto.WeatherReport
import ru.weather.model.FeedModel
import ru.weather.model.FeedModelState
import ru.weather.repository.WeatherRepository
import ru.weather.util.Translator
import javax.inject.Inject

//@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    private var myLocation = MutableLiveData<Pair<Double, Double>>()
    private var locality = MutableLiveData<String>()

    private var _citySearchResult = MutableLiveData<List<CitySearchResult>?>()
    val citySearchResult: LiveData<List<CitySearchResult>?>
        get() = _citySearchResult

    private var _nowWeather = MutableLiveData<ru.weather.dto.List>()
    val nowWeather: LiveData<ru.weather.dto.List>
        get() = _nowWeather

    val data: LiveData<FeedModel> = repository.data
        .map { weather ->
            FeedModel(weather)
        }.asLiveData(Dispatchers.Default)

    val dayData: LiveData<FeedModel> = repository.data
        .map { weather ->
            FeedModel(weather.take(8))
        }.asLiveData(Dispatchers.Default)

    private var _cityData = MutableLiveData<City>()
    val cityData: LiveData<City>
        get() = _cityData

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private var _emptyDataState = MutableLiveData<Boolean>()
    val emptyDataState: LiveData<Boolean>
        get() = _emptyDataState

    fun init() {
        myLocation = MutableLiveData()
        locality = MutableLiveData()
        Translator.downloadModal()
    }

    fun sendMyLocation(location: Pair<Double, Double>) {
        myLocation.value = location
    }

    fun sendLocality(state: String) {
        locality.value = state
    }

    fun getMyLocation(): LiveData<Pair<Double, Double>>? {
        return myLocation
    }

    fun getLocality(): LiveData<String>? {
        return locality
    }

    // _dataState.value = FeedModelState(loading = true)

    fun get(lat: Double, lon: Double, state: String) {
        repository.get(lat, lon,
            object : ApiService.WeatherCallback<WeatherReport> {
                override fun onSuccess(result: WeatherReport) {
                    clearData()
                    result.list?.map { it.weather.first() }
                    result.city?.state = state
                    result.list?.let { result.city?.let { it1 -> fillDb(it, it1) } }
                    println(result)
                }
//                    if (result.isEmpty()) _dataState.postValue(FeedModelState(error = true))
//                    else _dataState.postValue(FeedModelState())

                override fun onError(exception: Exception) {
                    println(exception)
                }
            }

        )
    }

    fun getCoordinates(city: String) {
        _citySearchResult.postValue(listOf(CitySearchResult()))
        repository.getCoordinates(
            city,
            object : ApiService.WeatherCallback<List<CitySearchResult>> {
                override fun onSuccess(result: List<CitySearchResult>) {
                    _citySearchResult.postValue(result.distinctBy { it.state })
                    println(result)
                }
//                    if (result.isEmpty()) _dataState.postValue(FeedModelState(error = true))
//                    else _dataState.postValue(FeedModelState())

                override fun onError(exception: Exception) {
                    println(exception)
                }
            })
    }

    fun fillDb(weather: List<ru.weather.dto.List>, city: City) =
        viewModelScope.launch {
            try {
                repository.fillDb(weather, city)
            } catch (e: Exception) {
                println(e)
            }
        }

    fun getNowWeather() = viewModelScope.launch {
        _nowWeather =
            repository.data
                .map { weather ->
                    weather.firstOrNull()
                }
                .asLiveData(Dispatchers.Default) as MutableLiveData<ru.weather.dto.List>
    }

    fun getCity() = viewModelScope.launch {
        _cityData =
            repository.cityData
                .map { city ->
                    city.lastOrNull()
                }
                .asLiveData(Dispatchers.Default) as MutableLiveData<City>
    }

    fun clearData() = viewModelScope.launch {
        repository.clear()
    }
}