package fstb.hobbiesly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.data.register.RegisterRequest
import fstb.hobbiesly.data.updateprofile.UpdateProfileRequest
import fstb.hobbiesly.domain.usecases.LoginUseCase
import fstb.hobbiesly.domain.usecases.RegisterUserUseCase
import fstb.hobbiesly.domain.usecases.UpdateProfileUseCase
import fstb.hobbiesly.util.Resource
import fstb.hobbiesly.view.home.UpdateProfileState
import fstb.hobbiesly.view.login.LoginState
import fstb.hobbiesly.view.login.RegisterState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthViewModel constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val registerState: MutableLiveData<RegisterState> = MutableLiveData()
    val _registerState: LiveData<RegisterState>
        get() = registerState

    private val loginState: MutableLiveData<LoginState> = MutableLiveData()
    val _loginState: LiveData<LoginState>
        get() = loginState

    private val updateProfileState: MutableLiveData<UpdateProfileState> = MutableLiveData()
    val _updateProfileState: LiveData<UpdateProfileState>
        get() = updateProfileState

    fun register(registerRequest: RegisterRequest){
        registerUserUseCase(registerRequest).onEach { result->
            when(result){
                is Resource.Success ->{
                    registerState.value = RegisterState(data = result.data)
                }
                is Resource.Loading ->{
                    registerState.value = RegisterState(isLoading = true)
                }
                is Resource.Error -> {
                    registerState.value = result.message?.let { RegisterState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login(loginRequest: LoginRequest){
        loginUseCase(loginRequest).onEach { result ->
            when(result){
                is Resource.Success ->{
                    loginState.value = LoginState(data = result.data)
                }

                is Resource.Loading -> {
                    loginState.value = LoginState(isLoading = true)
                }

                is Resource.Error -> {
                    loginState.value = result.message?.let { LoginState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfile(updateProfileRequest: UpdateProfileRequest){
        updateProfileUseCase(updateProfileRequest).onEach { result ->
            when(result){
                is Resource.Success ->{
                    updateProfileState.value = UpdateProfileState(data = result.data)
                }

                is Resource.Loading -> {
                    updateProfileState.value = UpdateProfileState(isLoading = true)
                }

                is Resource.Error -> {
                    updateProfileState.value = result.message?.let { UpdateProfileState(error = it) }
                }
            }
        }
    }
}