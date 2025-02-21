package ru.weather.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.weather.BuildConfig.ICON_URL
import ru.weather.databinding.CardWeatherBinding
import ru.weather.dto.List
import ru.weather.glide.load
import ru.weather.util.DateConverter
import ru.weather.util.WindDirection

class WeatherAdapter() :
    ListAdapter<List, WeatherViewHolder>(WeatherDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            CardWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position) ?: return
        holder.bind(weather)
    }
}

class WeatherViewHolder(
    private val binding: CardWeatherBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(weather: List) {
        with(binding) {
            weather.dt?.let { DateConverter.timeConvert(time, it) }
            iconWeather.load("$ICON_URL${weather.weather.first()?.icon}@2x.png")
            tempNow.text =
                "${weather.main?.temp?.let { Math.round(it).toString() }} \u2103"
            feelsLike.text =
                "${weather.main?.feels_like?.let { Math.round(it).toString() }} \u2103"
            windSpeed.text =
                "${weather.wind?.speed?.let { Math.round(it).toString() }} м/с"
            weather.wind?.deg?.let {
                WindDirection.convertWindDirection(
                    windDirection,
                    it, windDeg
                )
            }

            windGust.text =
                "${weather.wind?.gust?.let { Math.round(it).toString() }} м/с"
            precipitation.text =
                "${weather.pop?.let { Math.round(it * 100).toString() }} %"
        }
    }
}

object WeatherDiffCallback : DiffUtil.ItemCallback<List>() {
    override fun areItemsTheSame(oldItem: List, newItem: List) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: List, newItem: List) =
        oldItem == newItem
}