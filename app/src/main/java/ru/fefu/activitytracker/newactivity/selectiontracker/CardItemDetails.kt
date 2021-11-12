package ru.fefu.activitytracker.newactivity.selectiontracker

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class CardItemDetails(private val viewHolder: RecyclerView.ViewHolder) : ItemDetailsLookup.ItemDetails<Long>() {
    override fun getPosition(): Int {
        return viewHolder.adapterPosition
    }

    override fun getSelectionKey(): Long {
        return viewHolder.itemId
    }
}