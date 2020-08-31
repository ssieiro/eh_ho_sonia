package io.keepcoding.eh_ho_sonia.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.Topic
import io.keepcoding.eh_ho_sonia.data.TopicsRepo
import io.keepcoding.eh_ho_sonia.topics.TopicsFragment
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val topicID = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, PostsFragment(topicID))
            .commit()


    }
}