package io.github.alirzaev.currencies.features.converter

import io.github.alirzaev.currencies.data.model.Currency

data class ConverterUiState(
    val inputCurrency: Currency?,
    val outputCurrency: Currency?,
    val input: Double? = null,
    val output: Double? = null
)
