package io.github.alirzaev.currencies

import io.github.alirzaev.currencies.data.source.remote.dto.Currency

data class MainUiState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val toastMessage: Int? = null
)