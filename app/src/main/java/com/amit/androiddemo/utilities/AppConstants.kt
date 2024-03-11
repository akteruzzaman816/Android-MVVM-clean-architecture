package com.amit.androiddemo.utilities

import com.amit.androiddemo.data.remote.network.DataState
import okhttp3.ResponseBody
import retrofit2.Response

object AppConstants {
    // end points
    const val GITHUB_REPO              = "search/repositories"

    // constants
    const val SEARCH_KEYWORD = "search_keyword"
    const val  TAG = "#X_"

    // JSON STATUS
    private const val JSON_VALID_KEY = "application/json"
    private const val CONTENT_TYPE_KEY = "content-type"

    // check api response
    fun isValidResponse(responseBody: DataState.Success<Response<ResponseBody>?>): Boolean {
        return responseBody.value?.headers()?.get(CONTENT_TYPE_KEY)?.contains(
            JSON_VALID_KEY, true
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

    fun addTenMinutesToCurrentTime(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val tenMinutesInMillis = 2 * 60 * 1000 // 10 minutes in milliseconds
        return currentTimeMillis + tenMinutesInMillis
    }

    fun validateApiCacheLifeTime(timestamp: Long): Boolean {
        val currentTimestamp = System.currentTimeMillis()
        return if (timestamp < currentTimestamp) {
            false
        } else if (timestamp > currentTimestamp) {
            true
        } else {
            true
        }
    }

}
