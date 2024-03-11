package com.amit.androiddemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "search_response")
data class SearchResponse(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("search_query")
    var searchQuery: String?,

    @SerializedName("response_data")
    var responseData: String?,

    @SerializedName("created_at")
    var createdAt: Long?,

    @SerializedName("valid_till")
    var validTill: Long?

) : Serializable

