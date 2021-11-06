package ru.fefu.activitytracker.newactivity.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel

class ActivityTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var activityTypeTextView: TextView? = null

    init {
        activityTypeTextView = itemView.findViewById(R.id.activity_type_start_card)
    }

    fun bindValues(typeModel: ActivityTypeModel) {
        activityTypeTextView?.text = typeModel.type
    }
}