package ru.weather.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.weather.BuildConfig.ICON_URL
import ru.weather.databinding.CardDayWeatherBinding
import ru.weather.dto.WeatherForDay
import ru.weather.glide.load
import ru.weather.util.DateConverter
import ru.weather.util.WindDirection

class WeatherForDayAdapter() :
    ListAdapter<WeatherForDay, WeatherForDayViewHolder>(WeatherForDayDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForDayViewHolder {
        val view =
            CardDayWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherForDayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForDayViewHolder, position: Int) {
        val weather = getItem(position) ?: return
        holder.bind(weather)
    }
}

class WeatherForDayViewHolder(
    private val binding: CardDayWeatherBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(weather: WeatherForDay) {
        with(binding) {
            weather.dateTime?.let { DateConverter.dateConvert(date, it) }
            weatherEvents.text = weather.weatherEvent?.replaceFirstChar(Char::uppercase)
            iconWeather.load("$ICON_URL${weather.icon}@2x.png")
            temp.text =
                "${
                    weather.temp_max?.let {
                        Math.round(it).toString()
                    }
                } .. ${weather.temp_min?.let { Math.round(it).toString() }} \u2103"
            windSpeed.text =
                "${weather.wind_speed?.let { Math.round(it).toString() }} м/с"
            weather.wind_deg?.let {
                WindDirection.convertWindDirection(
                    windDirection,
                    Math.round(it), windDeg
                )
            }
            windGust.text =
                "${weather.wind_gust?.let { Math.round(it).toString() }} м/с"
            precipitation.text =
                "${weather.precipitation?.let { Math.round(it * 100).toString() }} %"
        }
    }
}

object WeatherForDayDiffCallback : DiffUtil.ItemCallback<WeatherForDay>() {
    override fun areItemsTheSame(oldItem: WeatherForDay, newItem: WeatherForDay) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: WeatherForDay, newItem: WeatherForDay) =
        oldItem == newItem
}