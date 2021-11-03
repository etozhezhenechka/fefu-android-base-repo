package ru.fefu.activitytracker.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.fragment.MyActivityDetailsFragment
import ru.fefu.activitytracker.tracker.fragment.UsersActivityDetailsFragment
import ru.fefu.activitytracker.tracker.model.CardItemModel
import ru.fefu.activitytracker.tracker.model.DateLabelModel
import ru.fefu.activitytracker.tracker.model.UsersActivityModel
import ru.fefu.activitytracker.tracker.viewholder.ActivityViewHolder
import ru.fefu.activitytracker.tracker.viewholder.UserActivityViewHolder
import ru.fefu.activitytracker.tracker.viewholder.DateLabelViewHolder
import ru.fefu.activitytracker.tracker.viewholder.ItemViewHolder

class UsersActivityAdapter(private val items: List<CardItemModel>,
                           private val fragmentManager: FragmentManager) : MyActivityAdapter(items, fragmentManager) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.users_activity_card, parent, false)
                UserActivityViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_label, parent, false)
                DateLabelViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindValues(items[position])

        if (holder is UserActivityViewHolder) {
            holder.itemView.setOnClickListener {
                val activeFragment = fragmentManager.fragments.firstOrNull { !it.isHidden }

                fragmentManager.beginTransaction().apply {
                    if (activeFragment != null) hide(activeFragment)

                    add(
                        R.id.fragment_view_activity,
                        UsersActivityDetailsFragment.newInstance(),
                        UsersActivityDetailsFragment.tag
                    )

                    addToBackStack(UsersActivityDetailsFragment.tag)

                    commit()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]::class) {
            UsersActivityModel::class -> 0
            DateLabelModel::class -> 1
            else -> -1
        }
    }
}