package ru.fefu.activitytracker.tracker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R

open class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var activityProgressTextView: TextView? = null
    var timeProgressTextView: TextView? = null
    var activityTypeTextView: TextView? = null
    var activityDateTextView: TextView? = null

    init {
        activityProgressTextView = itemView.findViewById(R.id.activity_progress_caption)
        timeProgressTextView = itemView.findViewById(R.id.time_progress_caption)
        activityTypeTextView = itemView.findViewById(R.id.activity_type_caption)
        activityDateTextView = itemView.findViewById(R.id.activity_date_caption)
    }
}