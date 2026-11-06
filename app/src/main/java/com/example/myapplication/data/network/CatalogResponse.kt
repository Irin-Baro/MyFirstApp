package com.example.myapplication.data.network

import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("iconUrl") val iconUrl: String? = null
)