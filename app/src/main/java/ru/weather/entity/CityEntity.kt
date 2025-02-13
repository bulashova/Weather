package ru.weather.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.weather.dto.City
import ru.weather.dto.Coord

//@Entity
//data class WeatherReportEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long?,
//    val cod: String?,
//    val message: String?,
//    val cnt: Long?,
//    //val list: Nothing? = null,
//
//    @Embedded(prefix = "city_")
//    val city: CityEmbeddable?
//) {
//    fun toDto() = WeatherReport(
//        id,
//        cod,
//        message,
//        cnt,
//        //list,
//        city?.toDto(),
//    )
//
//    companion object {
//        fun fromDto(dto: WeatherReport) =
//            WeatherReportEntity(
//                dto.id,
//                dto.cod,
//                dto.message,
//                dto.cnt,
//                //dto.list,
//
//                dto.city?.let { CityEmbeddable.fromDto(it) },
//            )
//    }
//}

@Entity
data class CityEntity(
//data class CityEmbeddable(
    @PrimaryKey
    val id: Long?,
    val name: String?,

    @Embedded(prefix = "coord_")
    val coord: CoordEmbeddable?,

    val country: String?,
    val population: Long?,
    val timezone: Long?,
    val sunrise: Long?,
    val sunset: Long?
) {
    fun toDto() = City(
        id,
        name,
        coord?.toDto(),
        country,
        population,
        timezone,
        sunrise,
        sunset
    )

    companion object {
        fun fromDto(dto: City) =
            CityEntity(
                dto.id,
                dto.name,
                dto.coord?.let { CoordEmbeddable.fromDto(it) },
                dto.country,
                dto.population,
                dto.timezone,
                dto.sunrise,
                dto.sunset
            )
    }
}


data class CoordEmbeddable(
    var lat: Double? = null,
    var lon: Double? = null
) {
    fun toDto() = Coord(
        lat,
        lon,
    )

    companion object {
        fun fromDto(dto: Coord) =
            CoordEmbeddable(
                dto.lat,
                dto.lon,
            )
    }
}