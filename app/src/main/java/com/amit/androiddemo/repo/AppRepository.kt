package com.amit.androiddemo.repo

import com.amit.androiddemo.data.remote.network.ApiService
import com.amit.androiddemo.data.remote.network.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val api: ApiService) : BaseRepository() {

    suspend fun getGithubRepo(
        query: String
    ) = safeApiCall {
        api.getGithubRepo(query)
    }
}