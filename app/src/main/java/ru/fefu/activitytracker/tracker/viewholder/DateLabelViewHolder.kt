package ru.fefu.activitytracker.tracker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R

class DateLabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dateTextView: TextView? = null

    init {
        dateTextView = itemView.findViewById(R.id.activity_date_label)
    }
}