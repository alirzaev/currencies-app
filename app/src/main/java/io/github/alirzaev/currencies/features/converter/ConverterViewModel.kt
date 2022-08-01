package io.github.alirzaev.currencies.features.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.currencies.data.model.Currency
import io.github.alirzaev.currencies.utils.convertCurrency
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableLiveData(ConverterUiState(null, null))

    val uiState = _uiState as LiveData<ConverterUiState>

    fun resetUiState() {
        _uiState.value = ConverterUiState(null, null)
    }

    fun setInputCurrency(currency: Currency) {
        _uiState.value = _uiState.value?.copy(inputCurrency = currency)
        performConversion(_uiState.value?.input)
    }

    fun setOutputCurrency(currency: Currency) {
        _uiState.value = _uiState.value?.copy(outputCurrency = currency)
        performConversion(_uiState.value?.input)
    }

    fun exchangeCurrencies() {
        val state = _uiState.value

        _uiState.value = _uiState.value?.copy(
            inputCurrency = state?.outputCurrency,
            outputCurrency = state?.inputCurrency
        )
        performConversion(state?.input)
    }

    fun performConversion(value: Double?) {
        _uiState.value = _uiState.value?.copy(input = value)

        if (value == null) {
            _uiState.value = _uiState.value?.copy(output = null)
        } else {
            val inputCurrency = _uiState.value?.inputCurrency
            val outputCurrency = _uiState.value?.outputCurrency

            if (inputCurrency != null && outputCurrency != null) {
                val output = convertCurrency(inputCurrency, outputCurrency, value)

                _uiState.value = _uiState.value?.copy(output = output)
            } else {
                _uiState.value = _uiState.value?.copy(output = null)
            }
        }
    }
}