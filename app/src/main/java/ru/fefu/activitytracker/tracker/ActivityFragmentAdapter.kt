package ru.fefu.activitytracker.tracker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ActivityFragmentAdapter(fragment: ActivityFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) MyActivityFragment.newInstance()
        else UsersActivityFragment.newInstance()
    }
}