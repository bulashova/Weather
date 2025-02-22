package ru.weather.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import ru.weather.BuildConfig
import ru.weather.dto.CitySearchResult
import ru.weather.dto.WeatherReport
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class ApiService {

    private companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    private val client = OkHttpClient.Builder()
        .run {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            } else {
                this
            }
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val typeToken = object : TypeToken<WeatherReport>() {}
    private val language = "ru"
    private val units = "metric"

    fun get(lat: Double, lon: Double, callback: WeatherCallback<WeatherReport>) {
        val request = Request.Builder()
            .url("${BASE_URL}data/2.5/forecast?lat=$lat&lon=$lon&appid=${BuildConfig.API_KEY}&lang=$language&units=$units")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    val responseBody =
                        response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        callback.onSuccess(Gson().fromJson(responseBody, typeToken))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    private val typeTokenCity = object : TypeToken<List<CitySearchResult>>() {}

    fun getCoordinates(city: String, callback: WeatherCallback<List<CitySearchResult>>) {
        val request = Request.Builder()
            .url("${BASE_URL}geo/1.0/direct?q=$city&limit=5&appid=${BuildConfig.API_KEY}&lang=$language")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    val responseBody =
                        response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        callback.onSuccess(Gson().fromJson(responseBody, typeTokenCity))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    interface WeatherCallback<T> {
        fun onSuccess(result: T)
        fun onError(exception: Exception)
    }
}