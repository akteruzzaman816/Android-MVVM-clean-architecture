package com.amit.androiddemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amit.androiddemo.data.local.model.ScreenshotData
import com.amit.androiddemo.data.local.model.SearchResponse

@Dao
interface AppDao {
    @Query("Select * from search_response where searchQuery =:keyword")
    suspend fun checkSearchKeyword(keyword: String): SearchResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResult(data: SearchResponse)

    @Query("DELETE FROM search_response where id =:id")
    suspend fun deleteCacheData(id:Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreenshot(data: ScreenshotData)

    @Query("DELETE FROM screen_shot where id =:id")
    suspend fun deleteScreenShotData(id:Long)

    @Query("DELETE FROM screen_shot WHERE createdAt < :threshold")
    suspend fun deleteOldScreenshotData(threshold: Long)

    @Query("DELETE FROM search_response WHERE createdAt < :threshold")
    suspend fun deleteOldCacheData(threshold: Long)


}
