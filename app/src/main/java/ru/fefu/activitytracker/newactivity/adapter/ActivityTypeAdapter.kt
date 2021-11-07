package ru.fefu.activitytracker.newactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel
import ru.fefu.activitytracker.newactivity.viewholder.ActivityTypeViewHolder

class ActivityTypeAdapter(
    private val items: List<ActivityTypeModel>
) : RecyclerView.Adapter<ActivityTypeViewHolder>() {

    lateinit var tracker: SelectionTracker<Long>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.start_activity_card, parent, false)
        return ActivityTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityTypeViewHolder, position: Int) {
        val isSelected = tracker.isSelected(getItemId(position))
        holder.bindValues(items[position], isSelected)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}