package fstb.hobbiesly.data.register

import com.google.gson.annotations.SerializedName
import java.sql.Date

class RegisterResponse (
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
    val birth_date : Date,
    @SerializedName("adress")
    val adress : String,
    @SerializedName("city")
    val city : String,
    @SerializedName("country")
    val country :String
    )