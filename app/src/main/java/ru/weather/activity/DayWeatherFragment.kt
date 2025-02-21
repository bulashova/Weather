package ru.weather.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R
import ru.weather.adapter.WeatherAdapter
import ru.weather.databinding.DayWeatherFragmentBinding
import ru.weather.util.DateConverter
import ru.weather.util.Translator
import ru.weather.viewmodel.WeatherViewModel
import java.util.Locale

@AndroidEntryPoint
class DayWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel by activityViewModels<WeatherViewModel>()
        val binding = DayWeatherFragmentBinding.inflate(layoutInflater, container, false)

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
                        findNavController().navigate(R.id.action_dayWeatherFragment_to_nowWeatherFragment)
                    }
                }
            }
        }

        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setHomeButtonEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(false)

        val adapter = WeatherAdapter()

        binding.list.adapter = adapter
        viewModel.dayData.observe(viewLifecycleOwner) { weather ->
            weather.weather.firstOrNull()?.dt?.let { DateConverter.dateConvert(binding.date, it) }
            adapter.submitList(weather.weather)
        }
        return binding.root
    }
}