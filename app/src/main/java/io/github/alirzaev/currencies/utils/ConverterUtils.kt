package io.github.alirzaev.currencies.utils

import io.github.alirzaev.currencies.utils.dto.Currency

data class ParseResult(
    val inputCurrency: Currency,
    val outputCurrency: Currency,
    val inputValue: Double,
    val outputValue: Double
)

fun parseExpression(currencies: Map<String, Currency>, expression: String): ParseResult? {
    val re = Regex("(\\d+(\\.\\d+)?)\\s+([A-Za-z]+)\\s+in\\s+([A-Za-z]+)")

    val matches = re.find(expression) ?: return null

    val valueIn = matches.groupValues[1].toDouble()
    val currIn = currencies[matches.groupValues[3].uppercase()]
    val currOut = currencies[matches.groupValues[4].uppercase()]

    return if (currIn != null && currOut != null) {
        val converted =
            (valueIn * currIn.value / currIn.nominal) / (currOut.value / currOut.nominal)

        ParseResult(currIn, currOut, valueIn, converted)
    } else {
        null
    }
}