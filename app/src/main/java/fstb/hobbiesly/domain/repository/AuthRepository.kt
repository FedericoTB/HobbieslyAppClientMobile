package fstb.hobbiesly.domain.repository

import fstb.hobbiesly.data.IHobApi
import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.data.login.LoginResponse
import fstb.hobbiesly.data.register.RegisterRequest
import fstb.hobbiesly.data.register.RegisterResponse
import fstb.hobbiesly.data.updateprofile.UpdateProfileRequest
import fstb.hobbiesly.data.updateprofile.UpdateProfileResponse

class AuthRepository  constructor(
    private val api: IHobApi
) {
    suspend fun register(request: RegisterRequest): RegisterResponse = api.register(request)
    suspend fun login(request: LoginRequest): LoginResponse = api.login(request)
    suspend fun updateProfile(request: UpdateProfileRequest): UpdateProfileResponse =
        api.updateProfile(request)
}