package ru.weather.util

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun utcToLocalDate(date: Long) =
        Instant.ofEpochSecond(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    @RequiresApi(Build.VERSION_CODES.O)
    fun utcToLocalDateWithoutTime(date: Long) =
        Instant.ofEpochSecond(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate().atStartOfDay()


    private fun utcToLocalDateTime(utc: Long) =
        Instant.ofEpochSecond(utc)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    //val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm")
    //val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")

    val formatter = DateTimeFormatter.ofPattern("d MMMM uuuu")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun dateConvert(id: TextView, date: Long) {
        val date = utcToLocalDateTime(date)
        id.text = date.format(formatter)
    }

//    fun dateTextConvert(date: Long) : String {
//        val date = utcToLocalDateTime(date)
//        return date.format(formatter)
//    }

    fun timeConvert(id: TextView, time: Long) {
        val time = utcToLocalDateTime(time)
        id.text = time.format(timeFormatter)
    }

    fun timeDayConvert(time: Long): String {
        val time = utcToLocalDateTime(time)
        return time.format(timeFormatter)
    }
}