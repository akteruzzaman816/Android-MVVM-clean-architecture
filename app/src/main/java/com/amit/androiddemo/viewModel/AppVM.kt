package com.amit.androiddemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit.androiddemo.data.local.model.SearchResponse
import com.amit.androiddemo.data.remote.network.DataState
import com.amit.androiddemo.data.repo.AppRepository
import com.amit.androiddemo.utilities.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class AppVM @Inject constructor(private val repository: AppRepository) : ViewModel() {

    // search repo data
    private val _repoListResponse: MutableLiveData<DataState<Response<ResponseBody>>> = MutableLiveData()
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

   // delete cache from db
    fun deleteCacheData(
       id:Long
    ) = viewModelScope.launch {
        repository.deleteCacheData(id)
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