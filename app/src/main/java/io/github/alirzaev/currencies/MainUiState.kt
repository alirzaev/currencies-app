package io.github.alirzaev.currencies

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRate

data class MainUiState(
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val isLoading: Boolean = false,
    val toastMessage: Int? = null
)