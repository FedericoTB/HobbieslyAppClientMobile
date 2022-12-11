package com.example.hobappclient.data.messages

import com.google.gson.annotations.SerializedName

data class MessageRequest (
    @SerializedName("content")
    val content :String,
    @SerializedName("room")
    val room : Int,
    @SerializedName("owner")
    val owner : Int,
        )
