package com.example.hobappclient.data.messages

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class MessageResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("owner")
    val owner : Int,
    @SerializedName("room")
    val room : Int,
    @SerializedName("content")
    val content : String,
    @SerializedName("created_at")
    val created_at : Date,
    @SerializedName("updated_at")
    val updated_at : Date
)
