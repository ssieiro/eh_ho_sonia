package io.keepcoding.eh_ho_sonia.data

import org.json.JSONObject

data class SignInModel(
    val username: String,
    val password: String
)

data class SignUpModel(
    val username: String,
    val email: String,
    val password: String
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("name", username)
            .put("username", username)
            .put("email", email)
            .put("password", password)
            .put("active", true)
            .put("approved", true)
    }
}

data class CreateTopicModel(
    val title: String,
    val content: String
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("title", title)
            .put("raw", content)
    }
}

data class CreatePostModel(
    val topicId: Int,
    val content: String
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("topic_id", topicId)
            .put("raw", content)
    }
}