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

    /**
     * this function checks api response whether it is valid or not
     * @param[responseBody] response body
     * @return boolean
     */
    fun isValidResponse(responseBody: DataState.Success<Response<ResponseBody>?>): Boolean {
        return responseBody.value?.headers()?.get(CONTENT_TYPE_KEY)?.contains(
            JSON_VALID_KEY, true
        )?: false
    }

    /**
     * generate boilerplate html for webView json preview
     * @param[json] json String
     * @return html code in String format
     */
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


    /**
     * this function adds 10 minutes with current time
     * @return converted time  in Long
     */
    fun addTenMinutesToCurrentTime(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val tenMinutesInMillis = 2 * 60 * 1000 // 10 minutes in milliseconds
        return currentTimeMillis + tenMinutesInMillis
    }

    /**
     * this function checks api cache lifetime
     * @param[timestamp] timestamp  Long
     * @return converted time  in Long
     */
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
