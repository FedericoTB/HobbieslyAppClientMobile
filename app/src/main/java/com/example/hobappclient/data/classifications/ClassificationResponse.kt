package com.example.hobappclient.data.classifications

import com.google.gson.annotations.SerializedName

data class ClassificationResponse (
    @SerializedName("id")
    val id :Int,
    @SerializedName("category")
    val category :Int,
    @SerializedName("group")
    val group :Int,

    )
