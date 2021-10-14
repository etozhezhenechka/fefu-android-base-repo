package ru.fefu.activitytracker.tracker

import androidx.fragment.app.Fragment

class MetaFragment(private val _newInstance: Fragment, val tag: String) {
    fun newInstance() : Fragment = _newInstance
}