package com.amit.androiddemo.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.amit.androiddemo.R
import com.amit.androiddemo.databinding.ActivityHomeBinding
import com.amit.androiddemo.utilities.AppConstants

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
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