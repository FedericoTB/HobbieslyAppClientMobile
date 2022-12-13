package com.example.hobappclient.data.categories

import com.google.gson.annotations.SerializedName

data class CategoryRequest (
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
        )
