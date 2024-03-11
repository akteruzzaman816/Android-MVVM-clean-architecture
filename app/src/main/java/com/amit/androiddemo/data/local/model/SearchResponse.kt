package com.amit.androiddemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "search_response")
data class SearchResponse(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long,

    @SerializedName("search_query")
    private var searchQuery: String?,

    @SerializedName("response_data")
    private var responseData: String?,

    @SerializedName("timestamp")
    private var timeStamp: Long?

) : Serializable

