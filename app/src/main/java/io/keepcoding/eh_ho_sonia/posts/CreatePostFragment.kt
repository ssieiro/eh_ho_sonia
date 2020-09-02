package io.keepcoding.eh_ho_sonia.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho_sonia.LoadingDialogFragment
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.CreatePostModel
import io.keepcoding.eh_ho_sonia.data.PostsRepo
import io.keepcoding.eh_ho_sonia.data.RequestError
import io.keepcoding.eh_ho_sonia.inflate
import io.keepcoding.eh_ho_sonia.topics.TAG_LOADING_DIALOG
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_post.inputContent
import kotlinx.android.synthetic.main.fragment_create_topic.*
import java.lang.IllegalArgumentException

class CreatePostFragment(topicId: String = "", title: String = ""): Fragment() {
    var createPostInteractionListener: CreatePostInteractionListener? = null
    val loadingDialogFragment: LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_post)
        LoadingDialogFragment.newInstance(message)
    }
    var topicId = topicId
    var topicTitle = title

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CreatePostInteractionListener)
            createPostInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${CreatePostFragment.CreatePostInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_topic_or_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> createPost()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        titleTopic.text = topicTitle
    }

    fun createPost() {
        if (isFormValid()){
            postPost()
        } else {
            showErrors()
        }

    }

    fun postPost() {
        enableLoadingDialog()
        val model = CreatePostModel(
            topicId.toInt(),
            inputContent.text.toString()
        )

        context?.let {
            PostsRepo.addPost(
                it.applicationContext,
                model,
                {
                   enableLoadingDialog(false)
                    createPostInteractionListener?.onPostCreated()
                },
                {
                    enableLoadingDialog(false)
                    handleError(it)
                }
            )
        }

    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled)
            loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG)
        else
            loadingDialogFragment.dismiss()
    }

    private fun handleError(error: RequestError) {
        val message =
            if (error.messageResId != null)
                getString(error.messageResId)
            else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrors() {
        if (inputContent.text.isEmpty())
            inputContent.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputContent.text.isNotEmpty()

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }
}
