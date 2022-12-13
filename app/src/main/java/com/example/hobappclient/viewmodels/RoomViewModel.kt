package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel :ViewModel(){
    private val roomLiveData: MutableLiveData<RoomResponse?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    fun getRoomObserver(): MutableLiveData<RoomResponse?> {
        return roomLiveData
    }

    fun makeApiCall(token: String, room_id: String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getRoomById(tokenf,room_id)
            if (response.isSuccessful && response.code()==200){
                val room = response.body()
                roomLiveData.postValue(room)
            }
        }
    }
}
