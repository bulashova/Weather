package ru.weather.util

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    private val currentDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val yesterday = currentDate.minusDays(1)

    @RequiresApi(Build.VERSION_CODES.O)
    private val weekAgo = currentDate.minusDays(7)

    @RequiresApi(Build.VERSION_CODES.O)
    private val twoWeekAgo = currentDate.minusDays(14)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun utcToLocalDate(published: Long) =
        Instant.ofEpochSecond(published)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()


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

    fun timeConvert(id: TextView, time: Long) {
        val time = utcToLocalDateTime(time)
        id.text = time.format(timeFormatter)
    }
}