package com.amit.androiddemo.repo

import com.amit.androiddemo.network.BaseRepository
import com.amit.androiddemo.network.HttpClient

class AppRepository : BaseRepository() {
    private val  api = HttpClient.getInstance()

    suspend fun getGithubRepo(
        query: String
    ) = safeApiCall {
        api.getGithubRepo(query)
    }
}