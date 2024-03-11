package com.amit.androiddemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amit.androiddemo.data.local.model.SearchResponse

@Dao
interface AppDao {
    @Query("Select * from search_response where searchQuery =:keyword")
    fun checkSearchKeyword(keyword: String): SearchResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: SearchResponse)

    @Query("DELETE FROM search_response where id =:id")
    suspend fun deleteCacheData(id:Long)

}
