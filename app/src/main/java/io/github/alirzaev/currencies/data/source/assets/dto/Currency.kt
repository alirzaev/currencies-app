package io.github.alirzaev.currencies.data.source.assets.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Currency(
    @SerializedName("ID")
    @Expose val id: String,

    @SerializedName("Nominal")
    @Expose
    val nominal: Int,

    @SerializedName("Name")
    @Expose
    val name: String,

    @SerializedName("EngName")
    @Expose
    val engName: String,
) : Serializable
