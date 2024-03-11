package com.amit.androiddemo.data.remote.network

import com.amit.androiddemo.utilities.AppConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(AppConstants.GITHUB_REPO)
    suspend fun getGithubRepo(
        @Query("q") query: String
    ): Response<ResponseBody>


}