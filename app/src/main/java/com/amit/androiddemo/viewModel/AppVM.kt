package com.amit.androiddemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit.androiddemo.network.DataState
import com.amit.androiddemo.repo.AppRepository
import com.amit.androiddemo.utilities.AppConstants
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class AppVM : ViewModel() {
    private val repository = AppRepository()

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

    // check api response
    fun isValidResponse(responseBody: DataState.Success<Response<ResponseBody>?>): Boolean {
        return responseBody.value?.headers()?.get(AppConstants.CONTENT_TYPE_KEY)?.contains(
            AppConstants.JSON_VALID_KEY, true
        )?: false
    }

    // setup json response for webView
    fun getHtmlData(json: String): String {
        return "<html><head><script type='text/javascript'>function displayJson() {" +
                "var json = " + json + ";" +
                "document.getElementById('json').innerText = JSON.stringify(json, null, 4);" +
                "}" +
                "</script></head>" +
                "<body onload='displayJson()'>" +
                "<pre id='json'></pre>" +
                "</body></html>"
    }

}