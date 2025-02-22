package ru.weather.util

import android.widget.TextView
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

object Translator {

    val conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.RUSSIAN)
        .build()
    val enRuTranslator = Translation.getClient(options)

    fun translateCityName(id: TextView, text: String?) {
        text?.let {
            enRuTranslator.translate(it)
                .addOnSuccessListener { result ->
                    id.text = result
                }
                .addOnFailureListener { exception ->
                    println(exception)
                }
        }
    }

    fun downloadModalAndTranslate(id: TextView, text: String?) {
        enRuTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translateCityName(id, text)
            }
            .addOnFailureListener { exception ->
                println(exception)
            }
    }

    fun downloadModal() {
        enRuTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener { result ->
                println(result)
            }
            .addOnFailureListener { exception ->
                println(exception)
            }
    }
}