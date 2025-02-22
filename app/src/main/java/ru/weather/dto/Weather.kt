package ru.weather.dto

import com.google.gson.annotations.SerializedName

data class WeatherReport(
    val id: Long? = null,
    val cod: String? = null,
    val message: String? = null,
    val cnt: Long? = null,
    val list: ArrayList<List>? = arrayListOf(),
    val city: City? = City()
)

data class List(
    val id: Long? = null,
    var dt: Long? = null,
    val main: Main? = null,
    val weather: kotlin.collections.List<Weather?> = listOf(), //погодные явления
    val clouds: Clouds? = null,
    val wind: Wind? = null,
    val visibility: Long? = null,
    val pop: Double? = null,  //вероятность осадков
    val rain: Rain? = null,
    val snow: Snow? = null,
    val sys: Sys? = null,  //время суток
    val dt_txt: String? = null
)

data class Main(
    val temp: Double? = null,
    val feels_like: Double? = null,
    val temp_min: Double? = null,
    val temp_max: Double? = null,
    val pressure: Long? = null,
    val sea_level: Long? = null,
    val grnd_level: Long? = null,
    val humidity: Long? = null,
    val temp_kf: Double? = null,
)

//погодные явления
data class Weather(
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null,
)

data class Clouds(
    val all: Long? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Long? = null,
    val gust: Double? = null,
)

data class Rain(
    @SerializedName("3h")val rainVolume: Double? = null,
)

data class Snow(
    @SerializedName("3h")val snowVolume: Double? = null,
)

//время суток
data class Sys(
    val pod: String? = null
)

data class City(
    val id: Long? = null,
    val name: String? = null,
    val coord: Coord? = Coord(),
    val country: String? = null,
    val population: Long? = null,
    val timezone: Long? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    var state: String? = null,
)

data class Coord(
    var lat: Double? = null,
    var lon: Double? = null
)