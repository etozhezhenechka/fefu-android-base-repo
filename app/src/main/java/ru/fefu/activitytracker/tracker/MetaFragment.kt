package ru.fefu.activitytracker.tracker

import androidx.fragment.app.Fragment

class MetaFragment(val buttonId: Int, private val _newInstance: Fragment, val tag: String) {
    fun newInstance() : Fragment = _newInstance
}