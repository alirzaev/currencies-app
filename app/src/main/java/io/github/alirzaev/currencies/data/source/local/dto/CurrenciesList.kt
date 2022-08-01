package io.github.alirzaev.currencies.data.source.local.dto

import io.github.alirzaev.currencies.data.model.Currency
import java.io.Serializable

data class CurrenciesList(
    val items: List<Currency>
) : Serializable
