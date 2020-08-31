package io.keepcoding.eh_ho_sonia.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.Post
import io.keepcoding.eh_ho_sonia.inflate
import io.keepcoding.eh_ho_sonia.topics.TopicsAdapter
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*


class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostHolder>()  {
    private val posts = mutableListOf<Post>()

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): PostHolder {
        val context = list.context
        val view = list.inflate(R.layout.item_post)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.post = post
    }

    fun setPosts(posts: List<Post>){
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: Post? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.labelAuthor.text= it.username
                    itemView.labelContent.text = it.content
                    itemView.labelDate.text = it.date.toString()
                }
            }
    }

}