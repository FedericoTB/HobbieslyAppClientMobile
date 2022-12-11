package com.example.hobappclient.data.memberships

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class MembershipResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("user")
    val user : Int,
    @SerializedName("group")
    val group :  Int,
    @SerializedName("date_joined")
    val date_joined : Date,
    @SerializedName("invite_reason")
    val invite_reason :String
)
