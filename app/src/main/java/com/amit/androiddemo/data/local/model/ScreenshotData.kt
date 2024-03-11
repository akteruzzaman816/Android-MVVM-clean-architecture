package com.amit.androiddemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "screen_shot")
data class ScreenshotData(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("image_path")
    var imagePath: String?,

    @SerializedName("created_at")
    var createdAt: Long?

) : Serializable

