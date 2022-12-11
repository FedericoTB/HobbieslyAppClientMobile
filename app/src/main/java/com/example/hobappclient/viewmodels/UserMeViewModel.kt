package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.users.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserMeViewModel:ViewModel() {
    private val userLiveData: MutableLiveData<UserResponse?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    fun getUserDataObserver(): MutableLiveData<UserResponse?> {
        return userLiveData
    }

    fun makeApiCall(token: String, userid: String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getUserME(tokenf,userid)
            if (response.isSuccessful && response.code()==200){
                val user = response.body()
                userLiveData.postValue(user)
            }
        }
    }
}