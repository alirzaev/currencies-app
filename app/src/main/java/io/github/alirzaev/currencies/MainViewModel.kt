package io.github.alirzaev.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.currencies.data.source.ExchangeRatesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : ViewModel() {
    private val _uiState = MutableLiveData(MainUiState())

    val uiState = _uiState as LiveData<MainUiState>

    init {
        fetchExchangeRates()
    }

    fun toastMessageShown() {
        _uiState.value = _uiState.value?.copy(toastMessage = null)
    }

    private fun showToastMessage(message: Int) {
        _uiState.value = _uiState.value?.copy(toastMessage = message)
    }

    fun fetchExchangeRates() {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val exchangeRates = exchangeRatesRepository.getExchangeRates()
                _uiState.value = _uiState.value?.copy(exchangeRates = exchangeRates, isLoading = false)
            } catch (ex: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    toastMessage = R.string.failed_to_fetch_data
                )
            }
        }
    }
}