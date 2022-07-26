package io.github.alirzaev.currencies.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.util.HashMap

data class ExchangeRatesInfo(
    @SerializedName("Date")
    @Expose
    val date: String,

    @SerializedName("PreviousDate")
    @Expose
    val previousDate: String,

    @SerializedName("PreviousURL")
    @Expose
    val previousURL: String,

    @SerializedName("Timestamp")
    @Expose
    val timestamp: String,

    @SerializedName("Valute")
    @Expose
    val exchangeRates: HashMap<String, ExchangeRate>
)