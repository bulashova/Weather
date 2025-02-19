package ru.weather.model

data class FeedModel(
    val weather: List<ru.weather.dto.List> = emptyList(),
)

data class FeedModelState(
    val loading: Boolean = false,
    val error: Boolean = false
)