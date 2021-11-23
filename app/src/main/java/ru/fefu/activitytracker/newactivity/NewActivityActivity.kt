package ru.fefu.activitytracker.newactivity

import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
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
        initMap()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
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

    private fun initMap() {
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.setMultiTouchControls(true)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        val mapController = binding.map.controller
        mapController.setZoom(17.8)
        val defaultPoint = GeoPoint(43.0301, 131.8872)
        mapController.setCenter(defaultPoint)
    }
}