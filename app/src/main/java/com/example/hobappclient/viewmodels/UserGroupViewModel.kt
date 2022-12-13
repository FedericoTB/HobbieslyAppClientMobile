package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserGroupViewModel :ViewModel(){
    private var userGroupLiveData: MutableLiveData<UserGroupResponse?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)


    fun getUserGroupObserver(): MutableLiveData<UserGroupResponse?> {
        return userGroupLiveData
    }

    fun makeApiCall(token: String, group_id :String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getUserGroupById(tokenf, group_id)
            if (response.isSuccessful && response.code()==200){
                val userGroup = response.body()
                userGroupLiveData.postValue(userGroup)
            }
        }
    }
}