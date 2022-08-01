package io.github.alirzaev.currencies.data.source.local

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.alirzaev.currencies.data.model.Currency
import io.github.alirzaev.currencies.data.source.local.dto.CurrenciesList
import javax.inject.Inject

class CurrenciesLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CurrenciesLocalDataSource {
    override fun getCurrencies(): List<Currency> {
        return PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString(CURRENCIES_KEY, null)
            ?.let {
                gson.fromJson(it, CurrenciesList::class.java).items
            } ?: emptyList()
    }

    override fun saveCurrencies(currencies: List<Currency>) {
        PreferenceManager
            .getDefaultSharedPreferences(context)
            .edit {
                putString(CURRENCIES_KEY, gson.toJson(CurrenciesList(currencies)))
            }
    }

    companion object {
        private val gson: Gson = GsonBuilder().create()

        private const val CURRENCIES_KEY = "CURRENCIES_KEY"
    }
}