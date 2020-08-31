package io.keepcoding.eh_ho_sonia.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.TopicsRepo
import io.keepcoding.eh_ho_sonia.inflate
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_topics.buttonCreate
import kotlinx.android.synthetic.main.fragment_topics_error.*
import java.lang.IllegalArgumentException

class TopicsErrorFragment: Fragment() {

    var topicsInteractionListener: TopicsFragment.TopicsInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_topics_error)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TopicsFragment.TopicsInteractionListener)
            topicsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${TopicsFragment.TopicsInteractionListener::class.java.canonicalName}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCreate.setOnClickListener {
            this.topicsInteractionListener?.onCreateTopic()
        }
        buttonRetry.setOnClickListener {
            this.topicsInteractionListener?.retryLoading()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> this.topicsInteractionListener?.onLogout()
        }
        return super.onOptionsItemSelected(item)
    }



}