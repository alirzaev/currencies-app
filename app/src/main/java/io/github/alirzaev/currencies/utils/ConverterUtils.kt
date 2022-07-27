package io.github.alirzaev.currencies.utils

import io.github.alirzaev.currencies.data.model.Currency

fun convertCurrency(input: Currency, output: Currency, value: Double): Double {
    return (value * input.exchangeRate / input.nominal) / (output.exchangeRate / output.nominal)
}