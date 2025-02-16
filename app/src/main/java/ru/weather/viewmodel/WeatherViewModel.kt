package ru.weather.viewmodel

//import ru.weather.model.FeedModel
//import ru.weather.model.FeedModelState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.weather.api.ApiService
import ru.weather.dto.City
import ru.weather.dto.CitySearchResult
import ru.weather.dto.WeatherReport
import ru.weather.repository.WeatherRepository
import javax.inject.Inject

//@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    private var myLocation = MutableLiveData<Pair<Double, Double>>()

    private var _citySearchResult = MutableLiveData<List<CitySearchResult>?>()
    val citySearchResult: LiveData<List<CitySearchResult>?>
        get() = _citySearchResult

//    val data: LiveData<FeedModel> = repository.data
//        .map { weather ->
//            FeedModel(weather)
//        }.asLiveData(Dispatchers.Default)

//    private val _dataState = MutableLiveData<FeedModelState>()
//    val dataState: LiveData<FeedModelState>
//        get() = _dataState
//
//    private var _emptyDataState = MutableLiveData<Boolean>()
//    val emptyDataState: LiveData<Boolean>
//        get() = _emptyDataState
//
    init {
//        myLocation = MutableLiveData()
//
    }

    // _dataState.value = FeedModelState(loading = true)

    fun get() {
        repository.get(
            object : ApiService.WeatherCallback<WeatherReport> {
                override fun onSuccess(result: WeatherReport) {
                    result.list?.map { it.weather?.first() }
                    result.list?.let { result.city?.let { it1 -> fillDb(it.toList(), it1) } }
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

    fun fillDb(weather: List<ru.weather.dto.List>, city: City) = viewModelScope.launch {
        try {
            repository.fillDb(weather, city)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun clearData() = viewModelScope.launch {
        repository.clear()
    }
}