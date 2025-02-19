package ru.weather.util

import android.widget.ImageView
import android.widget.TextView
import ru.weather.R

object WindDirection {
    fun convertWindDirection(id: TextView, deg: Long, image: ImageView) {
        when (deg) {
            in 0L..22L -> {
                id.text = "С"
                image.setImageResource(R.drawable.south_24px)
            }

            in 23L..67L -> {
                id.text = "С/В"
                image.setImageResource(R.drawable.south_west_24px)
            }

            in 68L..112L -> {
                id.text = "В"
                image.setImageResource(R.drawable.west_24px)
            }

            in 113L..157L -> {
                id.text = "Ю/В"
                image.setImageResource(R.drawable.north_west_24px)
            }

            in 158L..202L -> {
                id.text = "Ю"
                image.setImageResource(R.drawable.north_24px)
            }

            in 203L..247L -> {
                id.text = "Ю/З"
                image.setImageResource(R.drawable.north_east_24px)
            }

            in 248L..292L -> {
                id.text = "З"
                image.setImageResource(R.drawable.east_24px)
            }

            in 293L..337L -> {
                id.text = "С/З"
                image.setImageResource(R.drawable.south_east_24px)
            }

            in 338L..360L -> {
                id.text = "С"
                image.setImageResource(R.drawable.south_24px)
            }
        }
    }
}