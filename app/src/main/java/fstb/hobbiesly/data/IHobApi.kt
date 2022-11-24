package fstb.hobbiesly.data

import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.data.login.LoginResponse
import fstb.hobbiesly.data.register.RegisterRequest
import fstb.hobbiesly.data.register.RegisterResponse
import fstb.hobbiesly.data.updateprofile.UpdateProfileRequest
import fstb.hobbiesly.data.updateprofile.UpdateProfileResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IHobApi {
    companion object{
        const val REGISTER = "register/"
        const val LOGIN = "loginng/"
        const val UPDATE_PROFILE = "update-profile/"
    }

    @POST(REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse
}