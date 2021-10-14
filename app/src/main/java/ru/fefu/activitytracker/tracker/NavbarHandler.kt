package ru.fefu.activitytracker.tracker

import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R


class NavbarHandler(private val fragments: List<MetaFragment>, private val fragmentManager: FragmentManager) {
    private var _activeMetaFragment = fragments[0]
    private lateinit var _hiddenMetaFragment : MetaFragment

    fun switchFragments(clickedButtonId: Int) {
        if (_activeMetaFragment.buttonId == clickedButtonId) return

        val activeFragment = fragmentManager.findFragmentByTag(_activeMetaFragment.tag)
        if (activeFragment != null) {
            fragmentManager.beginTransaction().apply {
                hide(activeFragment)
                commit()
            }
        }

        _hiddenMetaFragment = fragments.filter { it.buttonId == clickedButtonId }[0]

        val hiddenFragment = fragmentManager.findFragmentByTag(_hiddenMetaFragment.tag)
        if (hiddenFragment != null) {
            fragmentManager.beginTransaction().apply {
                show(hiddenFragment)
                commit()
            }
        }
        else {
            fragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    _hiddenMetaFragment.newInstance(),
                    _hiddenMetaFragment.tag
                )
                commit()
            }
        }

        _activeMetaFragment = _hiddenMetaFragment
    }
}