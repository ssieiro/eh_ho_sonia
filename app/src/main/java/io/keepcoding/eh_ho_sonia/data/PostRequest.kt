package io.keepcoding.eh_ho_sonia.data

import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho_sonia.BuildConfig
import org.json.JSONObject

class PostRequest(
    method: Int,
    url: String,
    body: JSONObject?,
    private val username: String? = null,
    listener: (response: JSONObject?) -> Unit,
    errorListener: (errorResponse: VolleyError) -> Unit
) : JsonObjectRequest(
    method, url, body, listener, errorListener
) {
    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Api-Key"] = BuildConfig.DiscourseApiKey

        username?.let {
            headers["Api-Username"] = it
        }

        return headers
    }
}