package com.amit.androiddemo.di

import com.amit.androiddemo.data.remote.network.ApiService
import com.amit.androiddemo.data.remote.network.HttpClient
import com.amit.androiddemo.repo.AppRepository
import com.amit.androiddemo.viewModel.AppVM
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


}
