package com.example.hobappclient.data.rooms

import com.google.gson.annotations.SerializedName

data class RoomRequest(
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("image_url")
    val image_url : String,
    @SerializedName("group")
    val group : Int,
    @SerializedName("owner")
    val owner : Int
)
