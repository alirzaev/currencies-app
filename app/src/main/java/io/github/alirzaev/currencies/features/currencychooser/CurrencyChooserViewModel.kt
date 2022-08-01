package io.github.alirzaev.currencies.features.currencychooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.currencies.data.model.Currency
import io.github.alirzaev.currencies.data.source.CurrenciesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyChooserViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {
    private val _currencies: MutableLiveData<List<Currency>> = MutableLiveData()

    val currencies = _currencies as LiveData<List<Currency>>

    fun fetchCurrencies() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                (currenciesRepository.getCachedCurrencies() + arrayListOf(
                    Currency(
                        "",
                        "643",
                        "RUB",
                        1,
                        "Российский рубль",
                        1.0,
                        1.0
                    )
                )).sortedBy { it.name }
            }

            _currencies.value = result.getOrDefault(emptyList())
        }
    }
}