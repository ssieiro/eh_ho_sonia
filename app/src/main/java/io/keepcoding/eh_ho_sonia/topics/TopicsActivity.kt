package io.keepcoding.eh_ho_sonia.topics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho_sonia.*
import io.keepcoding.eh_ho_sonia.data.Topic
import io.keepcoding.eh_ho_sonia.data.UserRepo
import io.keepcoding.eh_ho_sonia.login.LoginActivity
import io.keepcoding.eh_ho_sonia.posts.EXTRA_TOPIC_ID
import io.keepcoding.eh_ho_sonia.posts.EXTRA_TOPIC_TITLE
import io.keepcoding.eh_ho_sonia.posts.PostsActivity

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity: AppCompatActivity(), TopicsFragment.TopicsInteractionListener,
CreateTopicFragment.CreateTopicInteractionListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        if(isFirstTimeCreated(savedInstanceState))
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, TopicsFragment())
                .commit()


    }

    private fun goToPosts(topic: Topic) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        intent.putExtra(EXTRA_TOPIC_TITLE, topic.title)
        startActivity(intent)
    }

    override fun onCreateTopic() {
        supportFragmentManager.beginTransaction()
                //se hace con el inicializador para que se pinte limpio cada vez
            .replace(R.id.fragmentContainer, CreateTopicFragment())
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    override fun onLogout() {
        //borro datos
        UserRepo.logout(this.applicationContext)
        //voy a la app inicial
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        //importante, para que la actividad en la que estaba se borre
        finish()
    }

    override fun onShowPosts(topic: Topic) {
        goToPosts(topic)
    }

    override fun topicsLoadingError() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TopicsErrorFragment())
            .commit()
    }

    override fun retryLoading() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TopicsFragment())
            .commit()
    }


    override fun onTopicCreated() {
        supportFragmentManager.popBackStack()
    }
}