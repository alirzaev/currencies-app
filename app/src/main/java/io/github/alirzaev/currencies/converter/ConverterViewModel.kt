package io.github.alirzaev.currencies.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.alirzaev.currencies.utils.dto.Currency

class ConverterViewModel : ViewModel() {
    private val _currencies = MutableLiveData<Map<String, Currency>>()

    val currencies = _currencies as LiveData<Map<String, Currency>>

    fun setCurrencies(data: Map<String, Currency>) {
        _currencies.value = data
    }
}
