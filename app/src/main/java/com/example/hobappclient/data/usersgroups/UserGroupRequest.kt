package com.example.hobappclient.data.usersgroups

import com.google.gson.annotations.SerializedName

data class UserGroupRequest (
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("image_url")
    val image_url:String,
    @SerializedName("owner")
    val owner: Int
        )
