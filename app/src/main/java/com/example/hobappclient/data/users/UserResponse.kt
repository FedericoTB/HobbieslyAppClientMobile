package com.example.hobappclient.data.users

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class UserResponse (
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    val username : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("lastname")
    val last_name : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("birth_date")
    val birth_date : Date,
    @SerializedName("adress")
    val adress : String,
    @SerializedName("city")
    val city : String,
    @SerializedName("country")
    val country : String,
    @SerializedName("image_url")
    val image_url : String,
        )