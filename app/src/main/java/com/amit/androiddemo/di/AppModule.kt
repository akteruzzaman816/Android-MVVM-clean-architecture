package com.amit.androiddemo.di

import android.app.Application
import com.amit.androiddemo.data.local.AppDatabase
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
    fun provideRepository(apiService: ApiService): AppRepository = AppRepository(apiService)

    @Singleton
    @Provides
    fun provideLocalDb(app: Application) = AppDatabase.getInstance(app)

}
