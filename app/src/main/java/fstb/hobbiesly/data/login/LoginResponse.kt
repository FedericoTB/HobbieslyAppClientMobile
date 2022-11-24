package fstb.hobbiesly.data.login

import com.google.gson.annotations.SerializedName
import java.sql.Date

class LoginResponse (
    @SerializedName("refresh")
    val refresh : String,
    @SerializedName("access")
    val access : String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("username")
    val username : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("last_name")
    val last_name : String
    )