package com.example.hobappclient.data.classifications

import com.google.gson.annotations.SerializedName

data class ClassificationRequest(
    @SerializedName("category")
    val category :Int,
    @SerializedName("group")
    val group :Int,
)
