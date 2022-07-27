package io.github.alirzaev.currencies.data.model

data class Currency(
    val id: String,

    val numCode: String,

    val charCode: String,

    val nominal: Int,

    val name: String,

    val exchangeRate: Double,

    val previousExchangeRate: Double,
)
