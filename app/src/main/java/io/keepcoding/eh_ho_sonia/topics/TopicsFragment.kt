package io.keepcoding.eh_ho_sonia.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.Topic
import io.keepcoding.eh_ho_sonia.data.TopicsRepo
import io.keepcoding.eh_ho_sonia.inflate
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_topics.viewLoading
import java.lang.IllegalArgumentException

class TopicsFragment : Fragment() {

    var topicsInteractionListener: TopicsInteractionListener? = null

    private val topicsAdapter: TopicsAdapter by lazy {
        val adapter = TopicsAdapter {
            this.topicsInteractionListener?.onShowPosts(it)
        }
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TopicsInteractionListener)
            topicsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${TopicsInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_topics)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCreate.setOnClickListener {
            this.topicsInteractionListener?.onCreateTopic()
        }

        topicsAdapter.setTopics(TopicsRepo.topics)

        listTopics.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listTopics.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listTopics.adapter = topicsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        loadTopics()
    }

    private fun loadTopics() {
        context?.let {
            enableLoading()
            TopicsRepo
                .getTopics(it.applicationContext,
                    {
                        topicsAdapter.setTopics(it)
                        enableLoading(false)
                    },
                    {
                        enableLoading(false)
                        // TODO: Manejo de errores
                    }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> this.topicsInteractionListener?.onLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        topicsInteractionListener = null
    }

    interface TopicsInteractionListener {
        fun onCreateTopic()
        fun onLogout()
        fun onShowPosts(topic: Topic)
    }

}