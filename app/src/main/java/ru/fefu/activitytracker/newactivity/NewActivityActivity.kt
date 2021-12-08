package ru.fefu.activitytracker.newactivity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.CustomZoomButtonsController
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityNewactivityBinding
import ru.fefu.activitytracker.newactivity.fragment.ProgressNewActivityFragment
import ru.fefu.activitytracker.newactivity.fragment.StartNewActivityFragment
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel

class NewActivityActivity : AppCompatActivity(R.layout.activity_newactivity) {
    private lateinit var binding: ActivityNewactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity(savedInstanceState)
        initMap()
    }

    override fun onResume() {
        super.onResume()
        binding.newActivityMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.newActivityMap.onPause()
    }

    private fun setupActivity(savedInstanceState: Bundle?) {
        binding.newActivityToolbar.setNavigationOnClickListener {
            finish()
        }

        if (savedInstanceState == null) {
            val lastActivity = App.INSTANCE.db.activityDao().getLastActivity()
            val newFragment: Fragment
            val newTag: String

            when {
                lastActivity == null -> {
                    newFragment = StartNewActivityFragment.newInstance()
                    newTag = StartNewActivityFragment.tag
                }
                lastActivity.isFinished -> {
                    newFragment = StartNewActivityFragment.newInstance()
                    newTag = StartNewActivityFragment.tag
                }
                else -> {
                    newFragment = ProgressNewActivityFragment.newInstance(
                        ActivityTypeModel(lastActivity.type).typeStr,
                        lastActivity.id.toLong()
                    )
                    newTag = ProgressNewActivityFragment.tag
                }
            }

            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_view_menu_new_activity, newFragment, newTag)
                commit()
            }
        }
    }

    private fun initMap() {
        Configuration.getInstance().load(
            this,
            getPreferences(Context.MODE_PRIVATE)
        )
        binding.newActivityMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.newActivityMap.setMultiTouchControls(true)
        binding.newActivityMap.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        binding.newActivityMap.minZoomLevel = 4.0

        binding.newActivityMap.post {
            binding.newActivityMap.zoomToBoundingBox(
                BoundingBox(
                    43.232111,
                    132.117062,
                    42.968866,
                    131.768039
                ),
                false
            )
        }
    }
}