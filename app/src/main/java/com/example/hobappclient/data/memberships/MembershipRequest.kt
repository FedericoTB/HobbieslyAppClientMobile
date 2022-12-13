package com.example.hobappclient.data.memberships

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class MembershipRequest (
    @SerializedName("user")
    val user : Int,
    @SerializedName("group")
    val group :  Int,
    @SerializedName("invite_reason")
    val invite_reason :String
        )
