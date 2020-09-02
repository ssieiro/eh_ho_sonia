package io.keepcoding.eh_ho_sonia.data

import android.net.Uri
import io.keepcoding.eh_ho_sonia.BuildConfig

object ApiRoutes {

    fun signIn(username: String) =
        uriBuilder()
            .appendPath("users")
            .appendPath("${username}.json")
            .build()
            .toString()

    fun signUp() =
        uriBuilder()
            .appendPath("users")
            .build()
            .toString()

    fun getTopics() =
        uriBuilder()
            .appendPath("latest.json")
            .build()
            .toString()

    fun createTopicOrPost() =
        uriBuilder()
            .appendPath("posts.json")
            .build()
            .toString()

    fun getPosts (id: String) =
        uriBuilder()
            .appendPath("t")
            .appendPath("${id}.json")
            .build()
            .toString()

    private fun uriBuilder() =
        Uri.Builder()
            .scheme("https")
            .authority(BuildConfig.DiscourseDomain)
}