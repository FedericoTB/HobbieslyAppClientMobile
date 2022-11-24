package fstb.hobbiesly.domain.usecases

import fstb.hobbiesly.domain.repository.AuthRepository
import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.data.login.LoginResponse
import fstb.hobbiesly.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase constructor(private val repository: AuthRepository){
    operator fun invoke(request: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.login(request)
            emit(Resource.Success(response))
        }catch (e: HttpException){
            emit(Resource.Error("An error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Check internet connection"))
        }
    }
}