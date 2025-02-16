package ru.weather.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import ru.weather.databinding.CardCityBinding
import ru.weather.dto.CitySearchResult
import java.util.Locale

class CitySearchResultAdapter() :
    ListAdapter<CitySearchResult, CitySearchResultViewHolder>(CitySearchResultDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchResultViewHolder {
        val view =
            CardCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitySearchResultViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CitySearchResultViewHolder, position: Int) {
        val city = getItem(position) ?: return
        holder.bind(city)
    }
}

class CitySearchResultViewHolder(
    private val binding: CardCityBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(city: CitySearchResult) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN)
            .build()
        val enRuTranslator = Translation.getClient(options)

        with(binding) {
            fun translateCityName() {
                city.name?.let {
                    enRuTranslator.translate(it)
                        .addOnSuccessListener { result ->
                            cityName.text = result
                        }
                        .addOnFailureListener { exception ->
                            println(exception)
                        }
                }
                city.state?.let {
                    enRuTranslator.translate(it)
                        .addOnSuccessListener { result ->
                            stateName.text = result
                        }
                        .addOnFailureListener { exception ->
                            println(exception)
                        }
                }
                city.country?.let {
                    countryName.text = Locale("", it).displayCountry
                }
            }

            fun downloadModalAndTranslate() {
                enRuTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        translateCityName()
                    }
                    .addOnFailureListener { exception ->
                        println(exception)
                    }
            }
            downloadModalAndTranslate()
        }
    }
}

object CitySearchResultDiffCallback : DiffUtil.ItemCallback<CitySearchResult>() {
    override fun areItemsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult) =
        oldItem == newItem
}