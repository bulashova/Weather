package ru.weather.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R
import ru.weather.databinding.UpdateDataFragmentBinding
import ru.weather.viewmodel.WeatherViewModel

@AndroidEntryPoint
class UpdateDataFragment : Fragment() {
    val viewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UpdateDataFragmentBinding.inflate(layoutInflater, container, false)

        activity?.setTitle(R.string.app_name)

        val actionBar: ActionBar? = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setHomeButtonEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(false)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                menu.clear()
            }
        }, viewLifecycleOwner)

        viewModel.getCity()
        viewModel.cityData.observe(viewLifecycleOwner) { city ->
            city.coord?.lat?.let {
                city.coord.lon?.let { it1 ->
                    city.state?.let { it2 ->
                        try {
                            viewModel.get(
                                it,
                                it1, it2
                            )
                        } catch (exception: Exception) {
                            findNavController().navigate(R.id.action_updateDataFragment_to_nowWeatherFragment)
                        }
                    }
                }
            }
            findNavController().navigate(R.id.action_updateDataFragment_to_nowWeatherFragment)
        }
        return binding.root
    }
}