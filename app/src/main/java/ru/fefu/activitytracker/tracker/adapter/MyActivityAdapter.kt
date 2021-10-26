package ru.fefu.activitytracker.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.model.ActivityModel
import ru.fefu.activitytracker.tracker.model.CardItemModel
import ru.fefu.activitytracker.tracker.model.DateLabelModel
import ru.fefu.activitytracker.tracker.viewholder.ActivityViewHolder
import ru.fefu.activitytracker.tracker.viewholder.DateLabelViewHolder
import ru.fefu.activitytracker.tracker.viewholder.ItemViewHolder

class MyActivityAdapter(private val items: List<CardItemModel>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_activity_card, parent, false)
                ActivityViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_label, parent, false)
                DateLabelViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]::class) {
            ActivityModel::class -> 0
            DateLabelModel::class -> 1
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindValues(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}