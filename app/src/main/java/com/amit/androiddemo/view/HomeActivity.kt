package com.amit.androiddemo.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amit.androiddemo.R
import com.amit.androiddemo.databinding.ActivityHomeBinding
import com.amit.androiddemo.utilities.AppConstants
import com.amit.androiddemo.viewModel.AppVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel:AppVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // search
            btnSearch.setOnClickListener {
                search()
            }
        }

        // check local data
        checkCacheData()

    }

    private fun checkCacheData() {
        val currentTime = System.currentTimeMillis()
        val threshold = currentTime - (30 * 60 * 1000) // 30 minutes in milliseconds

        // delete 30 minutes old data
        viewModel.deleteOldScreenshot(threshold)
        viewModel.deleteOldCacheData(threshold)
    }

    private fun search() = with(binding) {

        val query = etRepo.text.toString()
        if (query.isNotEmpty()) {
            // navigate to result page
            startActivity(Intent(this@HomeActivity, ResultViewActivity::class.java).
            putExtra(AppConstants.SEARCH_KEYWORD , query))
        } else {
            etRepo.apply {
                requestFocus()
                error = context.getString(R.string.query)
            }
        }
    }

}