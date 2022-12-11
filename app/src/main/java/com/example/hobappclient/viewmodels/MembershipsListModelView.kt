package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.data.memberships.MembershipResponse
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MembershipsListModelView :ViewModel(){
    private var membershipsListLiveData: MutableLiveData<ArrayList<MembershipResponse>?> = MutableLiveData()
    private val iHobApi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    fun getClassificationsListObserver(): MutableLiveData<ArrayList<MembershipResponse>?> {
        return membershipsListLiveData
    }

    fun makeApiCall(token:String){
        val tokenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO){
            val response = iHobApi.getMemberships(tokenf)
            if (response.isSuccessful && response.code()==200) {
                val membershipsList = response.body()
                membershipsListLiveData.postValue(membershipsList)
            }
        }
    }
}
