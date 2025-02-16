package ru.weather.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.weather.ui.theme.WeatherTheme
import androidx.navigation.findNavController
import ru.weather.viewmodel.WeatherViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.R

@AndroidEntryPoint

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: WeatherViewModel by viewModels<WeatherViewModel>()
//    private lateinit var mFusedLocationClient: FusedLocationProviderClient
//    private val permissionId = 2

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        getLocation()

        var currentMenuProvider: MenuProvider? = null

        currentMenuProvider?.let {
            removeMenuProvider(it)
        }
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.another -> {
                        //findNavController(R.id.nav_host_fragment_container).navigate(R.id.searchFragment)
                        true
                    }

                    else -> false
                }
        }.also {
            currentMenuProvider = it
        })


    }
}

//class MainActivity : ComponentActivity() {
//    private val viewModel: WeatherViewModel by viewModels<WeatherViewModel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            WeatherTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//
//        viewModel.get()
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WeatherTheme {
//        Greeting("Android")
//    }
//}