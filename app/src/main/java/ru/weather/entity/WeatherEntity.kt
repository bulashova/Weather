package ru.weather.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.weather.dto.Clouds
import ru.weather.dto.Main
import ru.weather.dto.Rain
import ru.weather.dto.Snow
import ru.weather.dto.Sys
import ru.weather.dto.Weather
import ru.weather.dto.Wind
import java.time.LocalDateTime

@Entity
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val dt: Long?,

    @Embedded(prefix = "main_")
    val main: MainEmbeddable?,

    @Embedded(prefix = "weather_")
    val weather: WeatherEmbeddable?, //погодные явления

    @Embedded(prefix = "clouds_")
    val clouds: CloudsEmbeddable?,

    @Embedded(prefix = "wind_")
    val wind: WindEmbeddable?,

    val visibility: Long?,
    val pop: Double?,  //вероятность осадков

    @Embedded(prefix = "rain_")
    val rain: RainEmbeddable?,

    @Embedded(prefix = "snow_")
    val snow: SnowEmbeddable?,

    @Embedded(prefix = "sys_")
    val sys: SysEmbeddable?,  //время суток

    val dt_txt: String?
) {
    fun toDto() = ru.weather.dto.List(
        id,
        dt,
        main?.toDto(),
        weather?.toDto() as List<Weather>,
        clouds?.toDto(),
        wind?.toDto(),
        visibility,
        pop,
        rain?.toDto(),
        snow?.toDto(),
        sys?.toDto(),
        dt_txt
    )

    companion object {
        fun fromDto(dto: ru.weather.dto.List) =
            ListEntity(
                dto.id,
                dto.dt,
                dto.main?.let { MainEmbeddable.fromDto(it) },
                dto.weather?.let { WeatherEmbeddable.fromDto(it.first()) },
                dto.clouds?.let { CloudsEmbeddable.fromDto(it) },
                dto.wind?.let { WindEmbeddable.fromDto(it) },
                dto.visibility,
                dto.pop,
                dto.rain?.let { RainEmbeddable.fromDto(it) },
                dto.snow?.let { SnowEmbeddable.fromDto(it) },
                dto.sys?.let { SysEmbeddable.fromDto(it) },
                dto.dt_txt,
            )
    }
}

data class MainEmbeddable(
    val temp: Double?,
    val feels_like: Double?,
    val temp_min: Double?,
    val temp_max: Double?,
    val pressure: Long?,
    val sea_level: Long?,
    val grnd_level: Long?,
    val humidity: Long?,
    val temp_kf: Double?,
) {
    fun toDto() = Main(
        temp,
        feels_like,
        temp_min,
        temp_max,
        pressure,
        sea_level,
        grnd_level,
        humidity,
        temp_kf
    )

    companion object {
        fun fromDto(dto: Main) =
            MainEmbeddable(
                dto.temp,
                dto.feels_like,
                dto.temp_min,
                dto.temp_max,
                dto.pressure,
                dto.sea_level,
                dto.grnd_level,
                dto.humidity,
                dto.temp_kf
            )
    }
}

data class WeatherEmbeddable(
    val id: Long?,
    val main: String?,
    val description: String?,
    val icon: String?,
) {
    fun toDto() = Weather(
        id,
        main,
        description,
        icon
    )

    companion object {
        fun fromDto(dto: Weather) =
            WeatherEmbeddable(
                dto.id,
                dto.main,
                dto.description,
                dto.icon
            )
    }
}

data class CloudsEmbeddable(
    val all: Long?,
) {
    fun toDto() = Clouds(
        all
    )

    companion object {
        fun fromDto(dto: Clouds) =
            CloudsEmbeddable(
                dto.all
            )
    }
}

data class WindEmbeddable(
    val speed: Double?,
    val deg: Long?,
    val gust: Double?
) {
    fun toDto() = Wind(
        speed,
        deg,
        gust
    )

    companion object {
        fun fromDto(dto: Wind) =
            WindEmbeddable(
                dto.speed,
                dto.deg,
                dto.gust
            )
    }
}

data class RainEmbeddable(
    val rainVolume: Double?,
) {
    fun toDto() = Rain(
        rainVolume
    )

    companion object {
        fun fromDto(dto: Rain) =
            RainEmbeddable(
                dto.rainVolume
            )
    }
}

data class SnowEmbeddable(
    val snowVolume: Double?,
) {
    fun toDto() = Snow(
        snowVolume
    )

    companion object {
        fun fromDto(dto: Snow) =
            SnowEmbeddable(
                dto.snowVolume
            )
    }
}

data class SysEmbeddable(
    val pod: String?,
) {
    fun toDto() = Sys(
        pod
    )

    companion object {
        fun fromDto(dto: Sys) =
            SysEmbeddable(
                dto.pod
            )
    }
}

fun List<ListEntity>.toDto(): List<ru.weather.dto.List> = map(ListEntity::toDto)
fun List<ru.weather.dto.List>.toEntity(): List<ListEntity> = map { ListEntity.fromDto(it) }