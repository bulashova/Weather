package ru.weather.util

object PressureConverter {
    private val index = 0.750063755419211
    fun pressureConvert(pr: Long) = Math.round(pr * index)
}