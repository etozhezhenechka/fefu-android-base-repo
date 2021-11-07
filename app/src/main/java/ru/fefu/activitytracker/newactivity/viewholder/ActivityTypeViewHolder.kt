package ru.fefu.activitytracker.newactivity.viewholder

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel

class ActivityTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var activityTypeTextView: TextView? = null
    private val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    init {
        activityTypeTextView = itemView.findViewById(R.id.activity_type_start_card)
    }

    fun bindValues(typeModel: ActivityTypeModel, isSelected: Boolean) {
        val cardView = itemView as com.google.android.material.card.MaterialCardView
        if (isSelected) {
            cardView.strokeColor = Color.parseColor("#4B09F3")
            cardView.strokeWidth = 2.px
        }
        else {
            cardView.strokeColor = Color.parseColor("#E0E0E0")
            cardView.strokeWidth = 1.px
        }
        activityTypeTextView?.text = typeModel.type
    }
}