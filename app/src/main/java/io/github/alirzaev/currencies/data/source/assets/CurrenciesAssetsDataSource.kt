package io.github.alirzaev.currencies.data.source.assets

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.alirzaev.currencies.data.source.CurrenciesDataSource
import io.github.alirzaev.currencies.data.source.assets.dto.Currency
import javax.inject.Inject

class CurrenciesAssetsDataSource @Inject constructor(
    private val context: Context
) : CurrenciesDataSource {
    override fun getCurrencies(): List<Currency> {
        val rawDate = context.assets.open("currencies.json").bufferedReader().use { it.readText() }

        return gson.fromJson(rawDate, currenciesListType)
    }

    companion object {
        private val gson: Gson = GsonBuilder().create()

        private val currenciesListType = object : TypeToken<List<Currency>>() {}.type
    }
}