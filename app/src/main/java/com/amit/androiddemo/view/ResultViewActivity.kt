package com.amit.androiddemo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.activity.viewModels
import com.amit.androiddemo.databinding.ActivityResultViewBinding
import com.amit.androiddemo.data.remote.network.model.ModelRepoListResponse
import com.amit.androiddemo.data.remote.network.DataState
import com.amit.androiddemo.utilities.AppConstants
import com.amit.androiddemo.viewModel.AppVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultViewActivity : AppCompatActivity() {
    private val TAG = "#X_"
    private lateinit var binding: ActivityResultViewBinding
    private val viewModel:AppVM  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // observe api response
        observeRepoResponse()

        // binding
        binding.apply {
            // back
            imgBack.setOnClickListener { finish() }
        }

        // bundle data
        checkArguments()
    }

    private fun checkArguments() {
        val query = intent.getStringExtra(AppConstants.SEARCH_KEYWORD) ?: ""
        // get repo data
        viewModel.getGithubRepo(query)
    }


    private fun observeRepoResponse() {
        viewModel.repoListResponse.observe(this) {
            when (it) {
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    val body = it.value.body()?.string()
                    if (!body.isNullOrBlank()) {
                        if (viewModel.isValidResponse(it)) {
                            val response = Gson().fromJson(body, ModelRepoListResponse::class.java)
                            setData(response)
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
    private fun setData(response: ModelRepoListResponse?) = with(binding){
        // set data to webView
        webView.apply {
            settings.javaScriptEnabled = true
            setInitialScale(100)
            webView.loadDataWithBaseURL(null, viewModel.getHtmlData(Gson().toJson(response)), "text/html", "utf-8", null)
        }
    }

}