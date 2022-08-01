package io.github.alirzaev.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.currencies.data.source.CurrenciesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {
    private val _uiState = MutableLiveData(MainUiState())

    val uiState = _uiState as LiveData<MainUiState>

    init {
        fetchExchangeRates(true)
    }

    fun toastMessageShown() {
        _uiState.value = _uiState.value?.copy(toastMessage = null)
    }

    private fun showToastMessage(message: Int) {
        _uiState.value = _uiState.value?.copy(toastMessage = message)
    }

    fun fetchExchangeRates(force: Boolean = false) {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val currencies = currenciesRepository.getCachedCurrencies()
                _uiState.value = _uiState.value?.copy(currencies = currencies)
            } catch (ex: Exception) {
            }

            if (force) {
                try {
                    val currencies = currenciesRepository.getCurrencies()
                    _uiState.value = _uiState.value?.copy(currencies = currencies)

                    currenciesRepository.saveCurrencies(currencies)
                } catch (ex: Exception) {
                    _uiState.value = _uiState.value?.copy(
                        toastMessage = R.string.failed_to_fetch_data
                    )
                }
            }

            _uiState.value = _uiState.value?.copy(isLoading = false)
        }
    }
}