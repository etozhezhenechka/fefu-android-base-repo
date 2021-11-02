package ru.fefu.activitytracker.tracker.viewholder

import android.view.View
import android.widget.TextView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.model.CardItemModel
import ru.fefu.activitytracker.tracker.model.DateLabelModel

class DateLabelViewHolder(itemView: View) : ItemViewHolder(itemView) {
    private var dateTextView: TextView? = null

    init {
        dateTextView = itemView.findViewById(R.id.activity_date_label)
    }

    override fun bindValues(itemModel: CardItemModel) {
        val dateModel = itemModel as DateLabelModel
        dateTextView?.text = dateModel.date
    }
}