package com.amit.androiddemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amit.androiddemo.data.local.dao.AppDao
import com.amit.androiddemo.data.local.model.ScreenshotData
import com.amit.androiddemo.data.local.model.SearchResponse

/**
 * Room database for local caching
 */
@Database(
    entities = [SearchResponse::class, ScreenshotData::class], version = 1, exportSchema = false
)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppDao(): AppDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app-db")
                .addCallback(object : Callback() {}).build()
        }
    }
}
