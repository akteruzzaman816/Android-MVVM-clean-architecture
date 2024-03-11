package com.amit.androiddemo.data.remote.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.time.DurationUnit
import kotlin.time.toTimeUnit

class HttpClient {
    companion object {
        private var instance: ApiService? = null

        @Synchronized
        fun getInstance(): ApiService {
            // cache for 10 minutes
            val maxCacheAge = DurationUnit.MINUTES.toTimeUnit().toMinutes(10)

            val cacheSetup = Interceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxCacheAge")
                    .removeHeader("Pragma")
                    .build()
            }


            if (instance == null) instance =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .client(getClient(cacheSetup)).build()
                    .create(ApiService::class.java)
            return instance as ApiService
        }

        private fun getClient(
            onlineInterceptor: Interceptor? = null
        ): OkHttpClient {
            return OkHttpClient.Builder().also { client ->
                client.addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Accept", "application/json")
                    }.build())
                }.also { builder ->
                    onlineInterceptor?.let { builder.addNetworkInterceptor(it) }
                }.build()
            }.build()
        }
    }
}
