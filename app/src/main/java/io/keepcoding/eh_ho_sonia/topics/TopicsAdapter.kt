package io.keepcoding.eh_ho_sonia.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.Topic
import io.keepcoding.eh_ho_sonia.inflate
import kotlinx.android.synthetic.main.item_topic.view.*
import java.util.*

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)? = null): RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {
    private val topics = mutableListOf<Topic>()

    private val listener: ((View) -> Unit) = {
        val tag: Topic = it.tag as Topic
        topicClickListener?.invoke(tag)
    }

    override fun getItemCount(): Int {
        return topics.size
    }
    //Le pasamos el xml con el diseño de la vista
    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): TopicHolder {
        val context = list.context
        val view = list.inflate(R.layout.item_topic)
        return TopicHolder(view)
    }
    //conectamos el modelo de datos con la vista
    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topics[position]
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()

    }

    inner class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.labelTitle.text = it.title
                    itemView.labelPosts.text = it.posts.toString()
                    itemView.labelViews.text = it.views.toString()
                    setTimeOffset(it.getTimeOffset())
                }
            }

        private fun setTimeOffset(timeOffset: Topic.TimeOffset) {

            val quantityString = when (timeOffset.unit) {
                Calendar.YEAR -> R.plurals.years
                Calendar.MONTH -> R.plurals.months
                Calendar.DAY_OF_MONTH -> R.plurals.days
                Calendar.HOUR -> R.plurals.hours
                else -> R.plurals.minutes
            }

            if (timeOffset.amount == 0) {
                itemView.labelDate.text =
                    itemView.context.resources.getString(R.string.minutes_zero)
            } else {
                itemView.labelDate.text =
                    itemView.context.resources.getQuantityString(
                        quantityString,
                        timeOffset.amount,
                        timeOffset.amount
                    )
            }
        }
    }

}