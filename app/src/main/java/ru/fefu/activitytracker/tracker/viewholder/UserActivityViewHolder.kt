package ru.fefu.activitytracker.tracker.viewholder

import android.view.View
import android.widget.TextView
import ru.fefu.activitytracker.R

class UserActivityViewHolder(itemView: View) : ActivityViewHolder(itemView) {
    var activityUserTextView: TextView? = null

    init {
        activityProgressTextView = itemView.findViewById(R.id.activity_progress_caption)
        timeProgressTextView = itemView.findViewById(R.id.time_progress_caption)
        activityTypeTextView = itemView.findViewById(R.id.activity_type_caption)
        activityDateTextView = itemView.findViewById(R.id.activity_date_caption)
        activityUserTextView = itemView.findViewById(R.id.activity_user_caption)
    }
}