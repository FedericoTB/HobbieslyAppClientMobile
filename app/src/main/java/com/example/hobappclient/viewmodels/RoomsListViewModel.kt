package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomsListViewModel:ViewModel() {
    private var roomsListLiveData: MutableLiveData<ArrayList<RoomResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)


    fun getRoomsListObserver(): MutableLiveData<ArrayList<RoomResponse>?> {
        return roomsListLiveData
    }

    fun makeApiCall(token: String, group_id:String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getRoomsByGroup(tokenf,group_id)
            if (response.isSuccessful && response.code()==200){
                val userGroupsList = response.body()
                roomsListLiveData.postValue(userGroupsList)
            }
        }
    }
}