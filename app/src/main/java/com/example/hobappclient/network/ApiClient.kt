package com.example.hobappclient.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        private const val API_URL = "http://10.0.2.2:8000/api/"

        fun getClient(): Retrofit {

        //interceptar la comunicacion
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val gsonBuilder = GsonBuilder()
        val gson : Gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    }
}