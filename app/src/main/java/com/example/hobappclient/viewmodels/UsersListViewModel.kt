package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.data.users.UserResponse
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersListViewModel : ViewModel(){
    private var usersListLiveData: MutableLiveData<ArrayList<UserResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)


    fun getUsersListObserver(): MutableLiveData<ArrayList<UserResponse>?> {
        return usersListLiveData
    }

    fun makeApiCall(token: String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getUsers(tokenf)
            if (response.isSuccessful && response.code()==200){
                val usersList = response.body()
                usersListLiveData.postValue(usersList)
            }
        }
    }
}
