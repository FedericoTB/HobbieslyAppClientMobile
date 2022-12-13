package fstb.hobbiesly.data.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username : String,
    @SerializedName("password")
    val password : String
)
