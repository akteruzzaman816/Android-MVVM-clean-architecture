package com.amit.androiddemo.di

import android.app.Application
import com.amit.androiddemo.data.local.AppDatabase
import com.amit.androiddemo.data.local.dao.AppDao
import com.amit.androiddemo.data.remote.network.ApiService
import com.amit.androiddemo.data.remote.network.HttpClient
import com.amit.androiddemo.data.repo.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = HttpClient.getInstance()

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService,appDao: AppDao): AppRepository = AppRepository(apiService,appDao)

    @Singleton
    @Provides
    fun provideLocalDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun providesAppDao(appDatabase: AppDatabase):AppDao =
        appDatabase.getAppDao()

}
