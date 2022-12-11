package com.example.hobappclient.data.usersgroups

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class UserGroupResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("created_at")
    val created_at: Date?,
    @SerializedName("owner")
    val owner: Int
        ) {

}
