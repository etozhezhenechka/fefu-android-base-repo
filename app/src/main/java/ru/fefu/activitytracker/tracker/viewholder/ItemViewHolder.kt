package ru.fefu.activitytracker.tracker.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.tracker.model.CardItemModel

abstract class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bindValues(itemModel: CardItemModel)
}