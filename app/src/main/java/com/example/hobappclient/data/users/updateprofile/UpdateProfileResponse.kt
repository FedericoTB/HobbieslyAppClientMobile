package fstb.hobbiesly.data.updateprofile

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("last_name")
    val last_name : String,
    @SerializedName("city")
    val city : String,
    @SerializedName("country")
    val country : String,
    @SerializedName("image_url")
    val image_url : String,
)
