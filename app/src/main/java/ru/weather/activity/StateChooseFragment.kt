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
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R
import ru.weather.adapter.CitySearchResultAdapter
import ru.weather.databinding.StateChooseFragmentBinding
import ru.weather.viewmodel.WeatherViewModel

@AndroidEntryPoint
class StateChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel by activityViewModels<WeatherViewModel>()
        val binding = StateChooseFragmentBinding.inflate(layoutInflater, container, false)

        activity?.setTitle(R.string.select_state)

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

        val adapter = CitySearchResultAdapter()

        binding.list.adapter = adapter
        viewModel.citySearchResult.observe(viewLifecycleOwner) { cities ->
            adapter.submitList(cities)
        }
        return binding.root
    }
}