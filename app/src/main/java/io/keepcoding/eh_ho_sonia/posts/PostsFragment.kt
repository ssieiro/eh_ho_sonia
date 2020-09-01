package io.keepcoding.eh_ho_sonia.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.PostsRepo
import io.keepcoding.eh_ho_sonia.inflate
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_topics.fragmentLoadingContainer
import kotlinx.android.synthetic.main.fragment_topics.viewLoading

class PostsFragment(postId: String = ""): Fragment() {
    val id = postId

    private val postAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter()
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter.setPosts(PostsRepo.posts)

        listPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //listPosts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listPosts.adapter = postAdapter
    }

    override fun onResume() {
        super.onResume()
        loadPosts(id)
    }

    fun loadPosts (postId: String) {
        context?.let {
            enableLoading()
            PostsRepo
                .getPosts(
                    it.applicationContext,
                    {
                        postAdapter.setPosts(it)
                        enableLoading(false)
                    },
                    {
                        enableLoading(false)
                        print("error")
                    },
                    postId
                )
        }
    }

    private fun enableLoading(enabled: Boolean = true)  {
        if (enabled) {
            fragmentLoadingContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentLoadingContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }

    }


}