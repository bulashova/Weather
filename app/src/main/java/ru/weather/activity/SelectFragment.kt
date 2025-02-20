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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R
import ru.weather.databinding.SelectFragmentBinding
import ru.weather.viewmodel.WeatherViewModel

@AndroidEntryPoint
class SelectFragment : Fragment() {

    private lateinit var location: Pair<Double, Double>
    private lateinit var state: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel by activityViewModels<WeatherViewModel>()
        val binding = SelectFragmentBinding.inflate(layoutInflater, container, false)

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

        with(binding) {
            locate.setOnClickListener {
                viewModel.getMyLocation()?.value?.let {
                    location = Pair(it.first, it.second)
                }
                viewModel.getLocality()?.value?.let {
                    state = it
                }

                try {
                    viewModel.get(location.first, location.second, state)
                } catch (e: Exception){

                    Snackbar.make(
                        root, R.string.check_internet_connection,
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.ok) {
                        }.show()

                }

                viewModel.data.observe(viewLifecycleOwner) { weather ->
                    if (weather.weather.isNotEmpty()) {
                        findNavController().navigate(R.id.action_selectFragment_to_nowWeatherFragment)
                    }
                }
            }
            search.setOnClickListener {
                findNavController().navigate(R.id.action_selectFragment_to_searchFragment)
            }
        }
        return binding.root
    }
}