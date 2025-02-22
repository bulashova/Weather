package ru.weather.dto

data class WeatherForDay(
    val dateTime: Long?,
    val weatherEvent: String?,
    val icon: String?,
    val temp_max: Double?,
    val temp_min: Double?,
    val wind_deg: Double?,
    val wind_speed: Double?,
    val wind_gust: Double?,
    val precipitation: Double?
)