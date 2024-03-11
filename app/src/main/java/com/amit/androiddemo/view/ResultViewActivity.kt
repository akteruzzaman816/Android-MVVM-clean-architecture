package com.amit.androiddemo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.activity.viewModels
import com.amit.androiddemo.data.local.model.SearchResponse
import com.amit.androiddemo.databinding.ActivityResultViewBinding
import com.amit.androiddemo.data.remote.model.ModelRepoListResponse
import com.amit.androiddemo.data.remote.network.DataState
import com.amit.androiddemo.utilities.AppConstants
import com.amit.androiddemo.viewModel.AppVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultViewActivity : AppCompatActivity() {
    private val TAG = "#X_"
    private lateinit var binding: ActivityResultViewBinding
    private var searchKeyword = ""
    private val viewModel:AppVM  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // observe api response
        observeRepoResponse()
        // observe local data
        observeCacheDataResponse()

        // binding
        binding.apply {
            // back
            imgBack.setOnClickListener { finish() }
        }

        // bundle data
        checkArguments()
    }

    private fun checkArguments() {
        // search keyword
        searchKeyword = intent.getStringExtra(AppConstants.SEARCH_KEYWORD) ?: ""
        // check cache data
        viewModel.checkSearchKeyword(searchKeyword)
    }

    private fun observeCacheDataResponse(){
        viewModel.checkData.observe(this){
            when(it){
                is DataState.Success -> {
                    if (it.value != null) {
                        // check cache value
                        checkValidCache(it.value)
                    }
                    else {
                        viewModel.getGithubRepo(searchKeyword)
                    }

                }
                is DataState.Error -> {}
                else -> {}
            }
        }
    }

    private fun checkValidCache(value: SearchResponse) {
        // time validation
        if (value.validTill?.let { AppConstants.validateCacheLifeTime(it) } == true){
            // parse cache
            val data = Gson().fromJson(value.responseData, ModelRepoListResponse::class.java)
            setData(data,false)
        }else {
            // delete data from cache
            viewModel.deleteCacheData(value.id)

            // api call for new data
            viewModel.getGithubRepo(searchKeyword)
        }
    }


    private fun observeRepoResponse() {
        viewModel.repoListResponse.observe(this) {
            when (it) {
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    val body = it.value?.body()?.string()
                    if (!body.isNullOrBlank()) {
                        if (AppConstants.isValidResponse(it)) {
                            val response = Gson().fromJson(body, ModelRepoListResponse::class.java)
                            setData(response,true)
                        } else Log.d(TAG, "observeAppointmentResponse: $body")
                    }
                }

                is DataState.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    Log.e(TAG, "$it")
                }

                else -> {}
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setData(response: ModelRepoListResponse?,isApiCalled:Boolean) = with(binding){
        // set data to webView
        webView.apply {
            settings.javaScriptEnabled = true
            setInitialScale(100)
            webView.loadDataWithBaseURL(null, AppConstants.getHtmlData(Gson().toJson(response)), "text/html", "utf-8", null)
        }

        // cache only valid data
        if (isApiCalled) insertResponseToCache(response)
    }

    private fun insertResponseToCache(response: ModelRepoListResponse?) {
        val searchData = SearchResponse(
            searchQuery = searchKeyword,
            responseData = Gson().toJson(response),
            createdAt = System.currentTimeMillis(),
            validTill = AppConstants.addTenMinutesToCurrentTime()
        )
        viewModel.insertSearchResult(searchData)
    }

}