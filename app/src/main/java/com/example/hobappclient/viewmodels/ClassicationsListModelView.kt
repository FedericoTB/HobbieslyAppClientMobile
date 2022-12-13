package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.classifications.ClassificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassicationsListModelView :ViewModel(){
    private var classificationsListLiveData: MutableLiveData<ArrayList<ClassificationResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    fun getClassificationsListObserver(): MutableLiveData<ArrayList<ClassificationResponse>?> {
        return classificationsListLiveData
    }

    fun makeApiCall(token:String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO){
            val response = ihobapi.getClassifications(tokenf)
            if (response.isSuccessful && response.code()==200) {
                val classificationsList = response.body()
                classificationsListLiveData.postValue(classificationsList)
            }
        }
    }
}
