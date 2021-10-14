package ru.fefu.activitytracker.tracker

import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R


class NavbarHandler(activeFragment: MetaFragment, hiddenFragment: MetaFragment,
                    fragmentManager: FragmentManager) {
    private var _currentButtonId = R.id.action_activity_tracker
    private var _activeFragment = activeFragment
    private var _hiddenFragment = hiddenFragment
    private val _fragmentManager = fragmentManager

    fun switchFragments(buttonId: Int) {
        if (_currentButtonId == buttonId) return

        val activeFrag = _fragmentManager.findFragmentByTag(_activeFragment.tag)
        if (activeFrag != null) {
            _fragmentManager.beginTransaction().apply {
                hide(activeFrag)
                commit()
            }
        }

        val hiddenFrag = _fragmentManager.findFragmentByTag(_hiddenFragment.tag)
        if (hiddenFrag != null) {
            _fragmentManager.beginTransaction().apply {
                show(hiddenFrag)
                commit()
            }
        }
        else {
            _fragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    _hiddenFragment.newInstance(),
                    _hiddenFragment.tag
                )
                commit()
            }
        }

        _activeFragment = _hiddenFragment.also { _hiddenFragment = _activeFragment }
        _currentButtonId = buttonId
    }
}