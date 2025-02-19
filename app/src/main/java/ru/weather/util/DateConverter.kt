package ru.weather.util

import android.widget.TextView
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateConverter {

    private fun utcToLocalDate(utc: Long) =
        Instant.ofEpochSecond(utc)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    //val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm")
    //val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
    val formatter = DateTimeFormatter.ofPattern("d MMMM uuuu")

    fun dateConvert(id: TextView, date: Long) {
        val date = utcToLocalDate(date)
        id.text = date.format(formatter)
    }
}