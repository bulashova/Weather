package ru.weather.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R
import ru.weather.adapter.WeatherForDayAdapter
import ru.weather.databinding.FiveDayWeatherFragmentBinding
import ru.weather.dto.WeatherForDay
import ru.weather.util.DateConverter
import ru.weather.util.Translator
import ru.weather.viewmodel.WeatherViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class FiveDaysWeatherFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setHomeButtonEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(false)

        val viewModel by activityViewModels<WeatherViewModel>()
        val binding = FiveDayWeatherFragmentBinding.inflate(layoutInflater, container, false)

        viewModel.getCity()
        viewModel.cityData.observe(viewLifecycleOwner) { city ->
            if (city == null) {
                null
            } else {
                with(binding) {
                    Translator.downloadModalAndTranslate(state, city.state)
                    activity?.setTitle(city.name)
                    city.country?.let {
                        country.text = Locale("", it).displayCountry
                    }
                    nowWeather.setOnClickListener {
                        findNavController().navigate(R.id.action_fiveDaysWeatherFragment_to_nowWeatherFragment)
                    }
                    todayWeather.setOnClickListener {
                        findNavController().navigate(R.id.action_fiveDaysWeatherFragment_to_dayWeatherFragment)
                    }
                }
            }
        }

        val adapter = WeatherForDayAdapter()
        binding.list.adapter = adapter

        val formatter = DateTimeFormatter.ofPattern("d MMMM uuuu")
        binding.date.text = LocalDate.now().format(formatter)

        fun weatherForDay(weather: List<ru.weather.dto.List>): WeatherForDay? {
            if (weather.firstOrNull()?.main?.temp == null) {
                return null
            }
            val dateDescription = weather.map { Pair(it.dt, it.weather.first()?.description) }
            val weatherEvent = dateDescription.find {
                DateConverter.timeDayConvert(it.first!!).equals("15:00")
            }?.second ?: weather.firstOrNull()?.weather?.firstOrNull()?.description
            val dateIcon = weather.map { Pair(it.dt, it.weather.first()?.icon) }
            val icon =
                dateIcon.find { DateConverter.timeDayConvert(it.first!!).equals("15:00") }?.second
                    ?: weather.firstOrNull()?.weather?.firstOrNull()?.icon
            val windDeg = weather.map { it.wind?.deg!! }
            return WeatherForDay(
                dateTime = weather.firstOrNull()?.dt,
                weatherEvent = weatherEvent,
                icon = icon,
                temp_max = weather.maxOfOrNull { it.main?.temp_max!! },
                temp_min = weather.minOfOrNull { it.main?.temp_min!! },
                wind_speed = weather.maxOfOrNull { it.wind?.speed!! },
                wind_deg = windDeg.sumOf { it.toDouble() } / windDeg.size,
                wind_gust = weather.maxOfOrNull { it.wind?.gust!! },
                precipitation = weather.sumOf { it.pop!! } / weather.size)
        }

        viewModel.data.observe(viewLifecycleOwner) { weather ->
            val currentDate = LocalDate.now().atStartOfDay()
            adapter.submitList(
                listOfNotNull(
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(0)) == true
                    }),
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(1)) == true
                    }),
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(2)) == true
                    }),
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(3)) == true
                    }),
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(4)) == true
                    }),
                    weatherForDay(weather.weather.filter {
                        it.dt?.let { it1 -> DateConverter.utcToLocalDateWithoutTime(it1) }
                            ?.isEqual(currentDate.plusDays(5)) == true
                    }),
                )
            )
        }
        return binding.root
    }
}