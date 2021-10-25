package ru.fefu.activitytracker.tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityTrackerBinding
import ru.fefu.activitytracker.tracker.fragment.ActivityFragment
import ru.fefu.activitytracker.tracker.fragment.MetaFragment
import ru.fefu.activitytracker.tracker.fragment.ProfileFragment

class TrackerActivity : AppCompatActivity(R.layout.activity_tracker) {
    private lateinit var binding: ActivityTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity(savedInstanceState)
        setupNavbarHandling()
    }

    private fun setupActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    ActivityFragment.newInstance(),
                    ActivityFragment.tag
                )
                commit()
            }
        }
    }

    private fun setupNavbarHandling() {
        val metaFragments = listOf(
            MetaFragment(R.id.action_activity_tracker, getFragment(ActivityFragment.tag),
                ActivityFragment.tag),
            MetaFragment(R.id.action_profile_tracker, getFragment(ProfileFragment.tag),
                ProfileFragment.tag)
        )

        val navbarHandler = NavbarHandler(metaFragments, supportFragmentManager)

        binding.navbarTracker.setOnItemSelectedListener { item ->
            navbarHandler.switchFragments(item.itemId)
            true
        }
    }

    private fun getFragment(tag: String) : Fragment {
        return supportFragmentManager.findFragmentByTag(tag)
            ?: when (tag) {
                ActivityFragment.tag -> ActivityFragment.newInstance()
                ProfileFragment.tag -> ProfileFragment.newInstance()
                else -> return Fragment()
            }
    }
}