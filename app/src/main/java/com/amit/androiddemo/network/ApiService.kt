package com.amit.androiddemo.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoints.GITHUB_REPO)
    suspend fun getGithubRepo(
        @Query("q") query: String
    ): Response<ResponseBody>


}