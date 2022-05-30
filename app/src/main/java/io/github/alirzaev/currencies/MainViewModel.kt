package io.github.alirzaev.currencies.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.currencies.MainUiState
import io.github.alirzaev.currencies.R
import io.github.alirzaev.currencies.data.source.CurrencyRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val _uiState = MutableLiveData(MainUiState())

    val uiState = _uiState as LiveData<MainUiState>

    init {
        fetchCurrencies()
    }

    fun toastMessageShown() {
        _uiState.value = _uiState.value?.copy(toastMessage = null)
    }

    private fun showToastMessage(message: Int) {
        _uiState.value = _uiState.value?.copy(toastMessage = message)
    }

    fun fetchCurrencies() {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val currencies = currencyRepository.getCurrencies()
                _uiState.value = _uiState.value?.copy(currencies = currencies, isLoading = false)
            } catch (ex: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    toastMessage = R.string.failed_to_fetch_data
                )
            }
        }
    }
}