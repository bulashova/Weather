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
import ru.weather.databinding.SearchFragmentBinding
import ru.weather.util.AndroidUtils
import ru.weather.viewmodel.WeatherViewModel

@AndroidEntryPoint
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel by activityViewModels<WeatherViewModel>()
        val binding = SearchFragmentBinding.inflate(layoutInflater, container, false)

        activity?.setTitle(R.string.search)

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
            search.setOnClickListener {
                val text = cityName.text?.toString()
                if (!text.isNullOrBlank()) {
                    viewModel.getCoordinates(cityName.text.toString())
                    findNavController().navigate(R.id.action_searchFragment_to_stateChooseFragment)
                } else {
                    AndroidUtils.hideKeyboard(requireView())
                    Snackbar.make(
                        binding.root, R.string.error_empty_name,
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                        .setAction(android.R.string.ok) {
                        }.show()
                }
            }
        }
        return binding.root
    }
}