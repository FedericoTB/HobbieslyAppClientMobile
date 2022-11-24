package fstb.hobbiesly.domain.usecases

import fstb.hobbiesly.domain.repository.AuthRepository
import fstb.hobbiesly.data.updateprofile.UpdateProfileRequest
import fstb.hobbiesly.data.updateprofile.UpdateProfileResponse
import fstb.hobbiesly.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UpdateProfileUseCase constructor(private val repository: AuthRepository) {
    operator fun invoke(request: UpdateProfileRequest): Flow<Resource<UpdateProfileResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.updateProfile(request)
            emit(Resource.Success(response))
        }catch (e: HttpException){
            emit(Resource.Error("An error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Check internet connection"))
        }
    }
}