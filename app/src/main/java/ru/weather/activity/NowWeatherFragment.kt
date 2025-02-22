package ru.weather.activity

import android.annotation.SuppressLint
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
import ru.weather.BuildConfig.ICON_URL
import ru.weather.R
import ru.weather.databinding.NowWeatherFragmentBinding
import ru.weather.glide.load
import ru.weather.util.DateConverter
import ru.weather.util.PressureConverter
import ru.weather.util.Translator
import ru.weather.util.WindDirection
import ru.weather.viewmodel.WeatherViewModel
import java.util.Locale

@AndroidEntryPoint
class NowWeatherFragment : Fragment() {
    val viewModel by activityViewModels<WeatherViewModel>()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NowWeatherFragmentBinding.inflate(layoutInflater, container, false)

        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setHomeButtonEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(false)

        viewModel.getCity()
        viewModel.getNowWeather()
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

                    todayWeather.setOnClickListener {
                        findNavController().navigate(R.id.action_nowWeatherFragment_to_dayWeatherFragment)
                    }
                    fiveDaysWeather.setOnClickListener {
                        findNavController().navigate(R.id.action_nowWeatherFragment_to_fiveDaysWeatherFragment)
                    }
                }
            }

            viewModel.nowWeather.observe(viewLifecycleOwner) { weather ->
                if (weather == null) {
                    null
                } else {
                    with(binding) {
                        weather.dt?.let { DateConverter.dateConvert(date, it) }
                        iconWeather.load("${ICON_URL}${weather.weather.first()?.icon}@2x.png")
                        tempNow.text =
                            "${weather.main?.temp?.let { Math.round(it).toString() }} \u2103"
                        weatherEvents.text =
                            weather.weather.first()?.description?.replaceFirstChar(Char::uppercase)
                                ?: ""
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
                        humidity.text = "${weather.main?.humidity.toString()} %"
                        clouds.text = "${weather.clouds?.all.toString()} %"

                        pressure.text = "${
                            weather.main?.pressure?.let { PressureConverter.pressureConvert(it) }
                                .toString()
                        } мм.рт.ст."

                        precipitation.text =
                            "${weather.pop?.let { Math.round(it * 100).toString() }} %"
                        visibility.text = "${weather.visibility.toString()} м"
                    }
                }
            }
        }
        return binding.root
    }
}