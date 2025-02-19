package ru.weather.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.weather.R


fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_loading_100dp)
        .error(R.drawable.ic_error_100dp)
        .timeout(30_000)
        .into(this)
}