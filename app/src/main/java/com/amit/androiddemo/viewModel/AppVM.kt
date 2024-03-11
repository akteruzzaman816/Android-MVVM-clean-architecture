package com.amit.androiddemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit.androiddemo.data.local.model.ScreenshotData
import com.amit.androiddemo.data.local.model.SearchResponse
import com.amit.androiddemo.data.remote.network.DataState
import com.amit.androiddemo.data.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AppVM @Inject constructor(private val repository: AppRepository) : ViewModel() {

    // search repo data
    private val _repoListResponse: MutableLiveData<DataState<Response<ResponseBody>>> =
        MutableLiveData()
    val repoListResponse: LiveData<DataState<Response<ResponseBody>>> get() = _repoListResponse

    fun getGithubRepo(
        query: String
    ) = viewModelScope.launch {
        _repoListResponse.value = DataState.Loading
        _repoListResponse.value = repository.getGithubRepo(query)
    }


    // insert search response to local db
    fun insertSearchResult(
        query: SearchResponse
    ) = viewModelScope.launch {
        repository.insertSearchResult(query)
    }

    // insert screenshot to local db
    fun insertScreenshot(
        data: ScreenshotData
    ) = viewModelScope.launch {
        repository.insertScreenshot(data)
    }

    // delete individual cache from db
    fun deleteCacheData(
        id: Long
    ) = viewModelScope.launch {
        repository.deleteCacheData(id)
    }

    // delete old screenshot from db
    fun deleteOldScreenshot(
        threshold: Long
    ) = viewModelScope.launch {
        repository.deleteOldScreenshotData(threshold)
    }

    // delete old cache from db
    fun deleteOldCacheData(
        threshold: Long
    ) = viewModelScope.launch {
        repository.deleteOldCacheData(threshold)
    }

    // check search keyword
    private val _checkData: MutableLiveData<DataState<SearchResponse>> = MutableLiveData()
    val checkData: LiveData<DataState<SearchResponse>> get() = _checkData
    fun checkSearchKeyword(
        keyword: String
    ) = viewModelScope.launch {
        _checkData.value = repository.checkSearchKeyword(keyword)
    }

}