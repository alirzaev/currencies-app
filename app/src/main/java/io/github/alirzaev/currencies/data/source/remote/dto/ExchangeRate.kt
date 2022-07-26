package io.github.alirzaev.currencies.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

data class ExchangeRate(
    @SerializedName("ID")
    @Expose val id: String,

    @SerializedName("NumCode")
    @Expose
    val numCode: String,

    @SerializedName("CharCode")
    @Expose
    val charCode: String,

    @SerializedName("Nominal")
    @Expose
    val nominal: Int,

    @SerializedName("Name")
    @Expose
    val name: String,

    @SerializedName("Value")
    @Expose
    val value: Double,

    @SerializedName("Previous")
    @Expose
    val previous: Double,
) : Serializable