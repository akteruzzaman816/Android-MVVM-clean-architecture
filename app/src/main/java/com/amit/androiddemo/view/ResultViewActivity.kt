package com.amit.androiddemo.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amit.androiddemo.data.local.model.ScreenshotData
import com.amit.androiddemo.data.local.model.SearchResponse
import com.amit.androiddemo.data.remote.model.ModelRepoListResponse
import com.amit.androiddemo.data.remote.network.DataState
import com.amit.androiddemo.databinding.ActivityResultViewBinding
import com.amit.androiddemo.utilities.AppConstants
import com.amit.androiddemo.utilities.AppConstants.TAG
import com.amit.androiddemo.viewModel.AppVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ResultViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultViewBinding
    private var searchKeyword = ""
    private var takeScreenShort = true
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
                        // take screen short for updated data
                        takeScreenShort = true
                    }

                }
                is DataState.Error -> {}
                else -> {}
            }
        }
    }

    private fun checkValidCache(value: SearchResponse) {
        // time validation
        if (value.validTill?.let { AppConstants.validateApiCacheLifeTime(it) } == true){
            // parse cache
            val data = Gson().fromJson(value.responseData, ModelRepoListResponse::class.java)
            setData(data,false)
            // no need to take screen short for old data
            takeScreenShort = false
        }else {
            // delete data from cache
            viewModel.deleteCacheData(value.id)

            // api call for new data
            viewModel.getGithubRepo(searchKeyword)
            // take screen short for updated data
            takeScreenShort = true
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
                        } else Log.d(TAG, "observeRepoResponse: $body")
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
            // setup webView client
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // When the page finishes loading, take a screenshot
                    takeScreenshot()
                }
            }
        }

        // cache only valid data
        if (isApiCalled){
            insertResponseToCache(response)
        }
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

    private fun takeScreenshot() = with(binding){
        val screenshotFileName = "response_ss_${System.currentTimeMillis()}.png"
        val screenshotFile = File(getExternalFilesDir(null), screenshotFileName)
            val bitmap = Bitmap.createBitmap(webView.measuredWidth, webView.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            webView.draw(canvas)

            // Save bitmap to file
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    FileOutputStream(screenshotFile).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                    // save to local db
                    viewModel.insertScreenshot(
                        ScreenshotData(
                            imagePath = screenshotFile.path,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

    }

}