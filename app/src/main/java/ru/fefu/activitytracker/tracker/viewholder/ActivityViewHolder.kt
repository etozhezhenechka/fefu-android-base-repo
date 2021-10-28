package ru.fefu.activitytracker.tracker.viewholder

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.model.ActivityModel
import ru.fefu.activitytracker.tracker.model.CardItemModel
import androidx.appcompat.app.AppCompatActivity




open class ActivityViewHolder(itemView: View) : ItemViewHolder(itemView) {
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

    override fun bindValues(itemModel: CardItemModel) {
        val activityModel = itemModel as ActivityModel
        activityProgressTextView?.text = activityModel.activityProgress
        timeProgressTextView?.text = activityModel.timeProgress
        activityTypeTextView?.text = activityModel.activityType
        activityDateTextView?.text = activityModel.activityDate
    }
}