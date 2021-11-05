package ru.fefu.activitytracker.newactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityNewactivityBinding
import ru.fefu.activitytracker.newactivity.fragment.StartNewActivityFragment

class NewActivityActivity : AppCompatActivity(R.layout.activity_newactivity) {
    private lateinit var binding: ActivityNewactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity(savedInstanceState)
    }

    private fun setupActivity(savedInstanceState: Bundle?) {
        binding.newActivityToolbar.setNavigationOnClickListener {
            finish()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_view_menu_new_activity,
                    StartNewActivityFragment.newInstance(),
                    StartNewActivityFragment.tag
                )
                commit()
            }
        }
    }
}