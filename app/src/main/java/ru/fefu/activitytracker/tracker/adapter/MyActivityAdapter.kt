package ru.fefu.activitytracker.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.fragment.MyActivityDetailsFragment
import ru.fefu.activitytracker.tracker.model.ActivityModel
import ru.fefu.activitytracker.tracker.model.CardItemModel
import ru.fefu.activitytracker.tracker.model.DateLabelModel
import ru.fefu.activitytracker.tracker.viewholder.ActivityViewHolder
import ru.fefu.activitytracker.tracker.viewholder.DateLabelViewHolder
import ru.fefu.activitytracker.tracker.viewholder.ItemViewHolder

open class MyActivityAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<CardItemModel, ItemViewHolder>(CardDiffCallback()) {

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
        return when (currentList[position]::class) {
            ActivityModel::class -> 0
            DateLabelModel::class -> 1
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindValues(currentList[position])

        if (holder is ActivityViewHolder) {
            holder.itemView.setOnClickListener {
                val activeFragment = fragmentManager.fragments.firstOrNull { !it.isHidden }

                fragmentManager.beginTransaction().apply {
                    if (activeFragment != null) hide(activeFragment)

                    add(
                        R.id.fragment_view_activity,
                        MyActivityDetailsFragment.newInstance(),
                        MyActivityDetailsFragment.tag
                    )

                    addToBackStack(MyActivityDetailsFragment.tag)

                    commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}