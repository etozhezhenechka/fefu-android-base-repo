package ru.fefu.activitytracker.tracker

import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R


class NavbarHandler(private val fragments: List<MetaFragment>, private val fragmentManager: FragmentManager) {
    private var _activeFragment = fragments[0]
    private lateinit var _hiddenFragment : MetaFragment

    fun switchFragments(buttonId: Int) {
        if (_activeFragment.buttonId == buttonId) return

        val activeFrag = fragmentManager.findFragmentByTag(_activeFragment.tag)
        if (activeFrag != null) {
            fragmentManager.beginTransaction().apply {
                hide(activeFrag)
                commit()
            }
        }

        _hiddenFragment = fragments.filter { it.buttonId == buttonId }[0]

        val hiddenFrag = fragmentManager.findFragmentByTag(_hiddenFragment.tag)
        if (hiddenFrag != null) {
            fragmentManager.beginTransaction().apply {
                show(hiddenFrag)
                commit()
            }
        }
        else {
            fragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    _hiddenFragment.newInstance(),
                    _hiddenFragment.tag
                )
                commit()
            }
        }

        _activeFragment = _hiddenFragment
    }
}