package io.keepcoding.eh_ho_sonia.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho_sonia.R
import org.json.JSONObject

object PostsRepo {

    val posts: MutableList<Post> = mutableListOf()

    fun getPosts(
        context: Context,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError)-> Unit,
        postId: String = ""
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getPosts(postId),
            null,
            {
                val list = Post.parsePostList(it)
                onSuccess(list)
            },
            {
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_no_internet)
                    else
                        RequestError(it)
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

}