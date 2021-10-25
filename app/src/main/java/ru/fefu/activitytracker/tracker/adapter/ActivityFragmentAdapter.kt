package ru.fefu.activitytracker.tracker.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fefu.activitytracker.tracker.fragment.ActivityFragment
import ru.fefu.activitytracker.tracker.fragment.MyActivityFragment
import ru.fefu.activitytracker.tracker.fragment.UsersActivityFragment

class ActivityFragmentAdapter(fragment: ActivityFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return itemsLen
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == firstPosition) MyActivityFragment.newInstance()
        else UsersActivityFragment.newInstance()
    }

    companion object {
        const val itemsLen = 2
        const val firstPosition = 0
    }
}