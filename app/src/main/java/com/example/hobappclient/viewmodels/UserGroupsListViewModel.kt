package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserGroupsListViewModel():ViewModel() {
    private var userGroupsListLiveData: MutableLiveData<ArrayList<UserGroupResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)


    fun getUserGroupsListObserver(): MutableLiveData<ArrayList<UserGroupResponse>?> {
        return userGroupsListLiveData
    }

    fun makeApiCall(token: String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getUserGroups(tokenf)
            if (response.isSuccessful && response.code()==200){
                val userGroupsList = response.body()
                userGroupsListLiveData.postValue(userGroupsList)
            }
        }
    }

    fun makeApiCallOwnUser(token: String, user_id :String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val membershipsResponse = ihobapi.getMemberships(tokenf)
            val response = ihobapi.getUserGroups(tokenf)
            if (response.isSuccessful && response.code()==200 &&
                membershipsResponse.isSuccessful && membershipsResponse.code()==200){
                val userGroupsList = response.body()
                val filteredgroupslist = membershipsResponse.body()?.filter { m-> m.user == user_id.toInt() }
                    ?.map { mf -> mf.group }?.toList()
                if (filteredgroupslist != null) {
                    if (userGroupsList != null) {
                        userGroupsListLiveData.postValue(userGroupsList.filter { ug -> filteredgroupslist.contains(ug.id) }as ArrayList<UserGroupResponse>)
                    }
                }
            }
        }
    }
}