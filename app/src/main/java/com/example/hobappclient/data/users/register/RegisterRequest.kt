package fstb.hobbiesly.data.register

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class RegisterRequest(
    @SerializedName("username")
    val username : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("last_name")
    val last_name : String,
    @SerializedName("gender")
    val gender :String,
    @SerializedName("birth_date")
    val birth_date : String,
    @SerializedName("adress")
    val adress : String,
    @SerializedName("city")
    val city : String,
    @SerializedName("country")
    val country :String,
    @SerializedName("password")
    val password :String,
    @SerializedName("image_url")
    val image_url : String
    )
