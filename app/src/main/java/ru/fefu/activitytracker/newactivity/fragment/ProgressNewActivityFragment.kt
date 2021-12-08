package ru.fefu.activitytracker.newactivity.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProgressNewActivityBinding
import ru.fefu.activitytracker.newactivity.service.ActivityLocationService

class ProgressNewActivityFragment : Fragment(R.layout.fragment_progress_new_activity) {
    private var _binding: FragmentProgressNewActivityBinding? = null
    private val binding get() = _binding!!
    private var mapView: org.osmdroid.views.MapView? = null
    private var isRestored = false
    private var activityId = -1L

    private val polyline by lazy {
        Polyline().apply {
            outlinePaint.color = ContextCompat.getColor(
                requireContext(),
                R.color.purple_700
            )
        }
    }

    companion object {
        const val tag = "progress_new_activity_fragment"

        fun newInstance(
            typeTitle: String,
            activityId: Long,
            restored: Boolean
        ): ProgressNewActivityFragment {
            val fragment = ProgressNewActivityFragment()

            val args = Bundle()
            args.putString("typeTitle", typeTitle)
            args.putLong("activityId", activityId)
            args.putBoolean("restored", restored)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressNewActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleProgressActivity.text = arguments?.getString("typeTitle")

        initLocation()

        binding.finishActivityBtn.setOnClickListener {
            finishActivity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUserLocation() {
        val locationOverlay = MyLocationNewOverlay(
            object : GpsMyLocationProvider(requireContext()) {
                private var mapMoved = false

                override fun onLocationChanged(location: Location) {
                    location.removeBearing()
                    super.onLocationChanged(location)
                    if (mapMoved) return
                    mapMoved = true
                    mapView?.controller?.animateTo(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        16.0,
                        1000
                    )
                }
            },
            mapView
        )

        val locationIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_user_location)
        locationOverlay.setDirectionArrow(locationIcon, locationIcon)
        locationOverlay.setPersonHotspot(locationIcon.width / 2f, locationIcon.height.toFloat())
        locationOverlay.enableMyLocation()
        mapView?.overlays?.add(locationOverlay)
    }

    private fun initLocation() {
        mapView = activity?.findViewById(R.id.new_activity_map)
        showUserLocation()

        isRestored = arguments?.getBoolean("restored")!!
        activityId = arguments?.getLong("activityId")!!

        App.INSTANCE.db.activityDao().getByIdLiveData(activityId.toInt())
            .observe(viewLifecycleOwner) {
                if (it.coordinateList.isNotEmpty()) {
                    if (isRestored) {
                        for (coordinate in it.coordinateList) {
                            polyline.addPoint(GeoPoint(coordinate.first, coordinate.second))
                        }
                        isRestored = false
                    } else {
                        val lastCoordinate = it.coordinateList.last()
                        polyline.addPoint(GeoPoint(lastCoordinate.first, lastCoordinate.second))
                    }

                    binding.distanceProgressActivity.text = getDistanceText()
                }
            }

        mapView?.overlayManager?.add(polyline)
    }

    private fun getDistanceText(): String {
        val distance = ActivityLocationService.distance

        return if (distance >= 1000) "%.2f км.".format(distance / 1000)
        else "%.2f м.".format(distance)
    }

    private fun finishActivity() {
        val cancelIntent = Intent(requireContext(), ActivityLocationService::class.java).apply {
            action = ActivityLocationService.ACTION_CANCEL
        }

        cancelIntent.putExtra("activityId", activityId)

        requireActivity().startService(cancelIntent)

        mapView?.overlays?.clear()
        mapView?.invalidate()

        App.INSTANCE.db.activityDao()
            .updateIsFinishedById(activityId.toInt(), true)

        App.INSTANCE.db.activityDao()
            .updateDistanceById(
                activityId.toInt(),
                ActivityLocationService.distance
            )

        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            requireActivity().finish()
        }
    }
}