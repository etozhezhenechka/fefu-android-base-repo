package ru.fefu.activitytracker.tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityTrackerBinding

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
        val navbarHandler = NavbarHandler(
            MetaFragment(ActivityFragment.newInstance(), ActivityFragment.tag),
            MetaFragment(ProfileFragment.newInstance(), ProfileFragment.tag),
            supportFragmentManager
        )

        binding.navbarTracker.setOnItemSelectedListener { item ->
            navbarHandler.switchFragments(item.itemId)
            true
        }
    }
}