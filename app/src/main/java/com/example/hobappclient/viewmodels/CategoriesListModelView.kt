package com.example.hobappclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.categories.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesListModelView :ViewModel(){
    private var categoryListLiveData : MutableLiveData<ArrayList<CategoryResponse>?> = MutableLiveData()
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    fun getCategoriesListObserver(): MutableLiveData<ArrayList<CategoryResponse>?> {
        return categoryListLiveData
    }

    fun makeApiCall(token: String){
        val tofenf = "Bearer $token"
        viewModelScope.launch(Dispatchers.IO) {
            val response = ihobapi.getCategories(tofenf)
            if (response.isSuccessful && response.code()==200){
                val categoriesList = response.body()
                categoryListLiveData.postValue(categoriesList)
            }
        }
    }
}
