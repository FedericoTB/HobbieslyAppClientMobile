package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.data.messages.MessageResponse
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesViewModel: ViewModel() {
    private var messagesListLiveData: MutableLiveData<ArrayList<MessageResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)


    fun getMessagesListObserver(): MutableLiveData<ArrayList<MessageResponse>?> {
        return messagesListLiveData
    }

    fun makeApiCall(token: String, room_id:String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getMessagesByRoom(tokenf,room_id)
            if (response.isSuccessful && response.code()==200){
                val messagesList = response.body()
                messagesListLiveData.postValue(messagesList)
            }
        }
    }
}