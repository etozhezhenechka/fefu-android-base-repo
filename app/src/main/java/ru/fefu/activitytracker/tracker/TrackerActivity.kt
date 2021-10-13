package ru.fefu.activitytracker.tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.R

class TrackerActivity : AppCompatActivity(R.layout.activity_tracker) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity(savedInstanceState)
    }

    private fun setupActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_tracker,
                    ActivityFragment.newInstance(),
                    ActivityFragment.tag
                )
                addToBackStack(ActivityFragment.tag)
                commit()
            }
        }
    }
}