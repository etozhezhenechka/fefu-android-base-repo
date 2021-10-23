package ru.fefu.activitytracker.tracker

import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R


class NavbarHandler(private val fragments: List<MetaFragment>, private val fragmentManager: FragmentManager) {
    private lateinit var _hiddenMetaFragment : MetaFragment

    fun switchFragments(clickedButtonId: Int) {
        val activeFragment = fragmentManager.fragments.firstOrNull { !it.isHidden }

        _hiddenMetaFragment = fragments.filter { it.buttonId == clickedButtonId }[0]
        val hiddenFragment = fragmentManager.findFragmentByTag(_hiddenMetaFragment.tag)

        if (activeFragment == hiddenFragment) return

        if (hiddenFragment == null) {
            fragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    _hiddenMetaFragment.fragment,
                    _hiddenMetaFragment.tag
                )
                commit()
            }
        }
        else {
            fragmentManager.beginTransaction().apply {
                show(hiddenFragment)
                commit()
            }
        }

        if (activeFragment != null) {
            fragmentManager.beginTransaction().apply {
                hide(activeFragment)
                commit()
            }
        }

    }
}