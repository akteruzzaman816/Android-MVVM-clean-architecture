package com.amit.androiddemo.data.repo

import com.amit.androiddemo.data.local.dao.AppDao
import com.amit.androiddemo.data.local.model.ScreenshotData
import com.amit.androiddemo.data.local.model.SearchResponse
import com.amit.androiddemo.data.remote.network.ApiService
import com.amit.androiddemo.data.remote.network.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val api: ApiService, private val appDao: AppDao) : BaseRepository() {


    // remote data
    suspend fun getGithubRepo(
        query: String
    ) = safeFetchData {
        api.getGithubRepo(query)
    }

    // local data
    suspend fun checkSearchKeyword(
        keyword: String
    ) = safeFetchData {
        appDao.checkSearchKeyword(keyword)
    }


    // insert search response
    suspend fun insertSearchResult(
        data: SearchResponse
    ) = safeFetchData {
        appDao.insertSearchResult(data)
    }

    // insert screenshot
    suspend fun insertScreenshot(
        data: ScreenshotData
    ) = safeFetchData {
        appDao.insertScreenshot(data)
    }

    // delete cache response
    suspend fun deleteCacheData(
        id: Long
    ) = safeFetchData {
        appDao.deleteCacheData(id)
    }


    // delete old screenshot data
    suspend fun deleteOldScreenshotData(threshold: Long) = safeFetchData {
        appDao.deleteOldScreenshotData(threshold)
    }
    // delete old cache data
    suspend fun deleteOldCacheData(threshold: Long) = safeFetchData {
        appDao.deleteOldCacheData(threshold)
    }

}