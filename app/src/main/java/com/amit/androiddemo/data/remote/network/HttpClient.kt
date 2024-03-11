package com.amit.androiddemo.data.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {
    companion object {
        private var instance: ApiService? = null

        @Synchronized
        fun getInstance(): ApiService {
            if (instance == null) instance =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/").build()
                    .create(ApiService::class.java)
            return instance as ApiService
        }


    }
}
