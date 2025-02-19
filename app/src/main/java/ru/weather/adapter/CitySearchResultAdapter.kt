package ru.weather.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.weather.databinding.CardCityBinding
import ru.weather.dto.CitySearchResult
import ru.weather.util.Translator
import java.util.Locale

interface OnInteractionListener {
    fun onSelect(city: CitySearchResult)
}

class CitySearchResultAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<CitySearchResult, CitySearchResultViewHolder>(CitySearchResultDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchResultViewHolder {
        val view =
            CardCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitySearchResultViewHolder(view, onInteractionListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CitySearchResultViewHolder, position: Int) {
        val city = getItem(position) ?: return
        holder.bind(city)
    }
}

class CitySearchResultViewHolder(
    private val binding: CardCityBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(city: CitySearchResult) {
        with(binding) {
            Translator.downloadModalAndTranslate(cityName, city.name)
            Translator.downloadModalAndTranslate(stateName, city.state)
            city.country?.let {
                countryName.text = Locale("", it).displayCountry
            }

            root.setOnClickListener {
                onInteractionListener.onSelect(city)
            }
        }
    }
}

object CitySearchResultDiffCallback : DiffUtil.ItemCallback<CitySearchResult>() {
    override fun areItemsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult) =
        oldItem == newItem
}