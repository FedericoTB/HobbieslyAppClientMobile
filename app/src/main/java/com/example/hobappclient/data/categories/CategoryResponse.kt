package com.example.hobappclient.data.categories

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("name")
    val name : String,
    val description: String
        )
