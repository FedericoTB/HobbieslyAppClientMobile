package com.example.hobappclient.data.rooms

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class RoomResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name :String,
    @SerializedName("description")
    val description : String,
    @SerializedName("image_url")
    val image_url : String,
    @SerializedName("created_at")
    val created_at : Date,
    @SerializedName("updated_at")
    val updated_at : Date,
    @SerializedName("group")
    val group : Int,
    @SerializedName("owner")
    val owner: Int
)
