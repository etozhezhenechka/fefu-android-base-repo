package ru.fefu.activitytracker.newactivity.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BuildConfig
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.database.Activity
import ru.fefu.activitytracker.databinding.FragmentStartNewActivityBinding
import ru.fefu.activitytracker.newactivity.adapter.ActivityTypeAdapter
import ru.fefu.activitytracker.newactivity.model.ActivityType
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel
import ru.fefu.activitytracker.newactivity.selectiontracker.CardDetailsLookup
import ru.fefu.activitytracker.newactivity.selectiontracker.CardPredicate
import ru.fefu.activitytracker.newactivity.service.ActivityLocationService
import java.time.LocalDateTime

class StartNewActivityFragment : Fragment(R.layout.fragment_start_new_activity) {
    private var _binding: FragmentStartNewActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var typeItems: MutableList<ActivityTypeModel>
    private var locationPermissionGranted: Boolean = false
    private lateinit var selectionTracker: SelectionTracker<Long>

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                locationPermissionGranted = true
                performChecksAndStartService()
            } else {
                locationPermissionGranted = false
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPermissionDeniedDialog()
                } else {
                    showRationaleDialog()
                }
            }
        }

    companion object {
        private const val REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR = 1337
        private const val REQUEST_CODE_RESOLVE_GPS_ERROR = 1338

        const val tag = "start_new_activity_fragment"

        fun newInstance(): StartNewActivityFragment {
            val fragment = StartNewActivityFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartNewActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView = binding.startActivityRecyclerView
        recycleView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        fillTypeList()

        val typeAdapter = ActivityTypeAdapter(typeItems)
        typeAdapter.setHasStableIds(true)

        recycleView.adapter = typeAdapter

        selectionTracker = SelectionTracker.Builder(
            "selection-id", recycleView,
            StableIdKeyProvider(recycleView), CardDetailsLookup(recycleView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(CardPredicate()).build()

        selectionTracker.select(ActivityType.BIKING.ordinal.toLong())

        (recycleView.adapter as ActivityTypeAdapter).tracker = selectionTracker

        binding.activityStartBtn.setOnClickListener {
            requestPermission()
            performChecksAndStartService()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillTypeList() {
        typeItems = mutableListOf(
            ActivityTypeModel(ActivityType.BIKING), ActivityTypeModel(ActivityType.RUNNING),
            ActivityTypeModel(ActivityType.SWIMMING)
        )
    }

    private fun showNewFragment() {
        val activeFragment = parentFragmentManager.fragments.firstOrNull { !it.isHidden }

        parentFragmentManager.beginTransaction().apply {
            if (activeFragment != null) hide(activeFragment)

            val typeTitle = ActivityTypeModel(
                ActivityType.values()[selectionTracker.selection.first().toInt()]
            ).typeStr

            add(
                R.id.fragment_view_menu_new_activity,
                ProgressNewActivityFragment.newInstance(typeTitle),
                ProgressNewActivityFragment.tag
            )

            addToBackStack(ProgressNewActivityFragment.tag)

            commit()
        }
    }

    private fun initProgressActivity() {
        val activityId = App.INSTANCE.db.activityDao().insert(
            Activity(
                0,
                ActivityType.values()[selectionTracker.selection.first().toInt()],
                0.0,
                LocalDateTime.of(2021, 1, 24, 12, 20),
                LocalDateTime.of(2021, 1, 24, 13, 41),
                mutableListOf()
            )
        )

        ActivityLocationService.startForeground(requireContext(), activityId.toInt())
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                locationPermissionGranted = true
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRationaleDialog()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Требуется разрешение")
            .setMessage("Вы не можете отслеживать свою активность без местоположения")
            .setPositiveButton("Запросить") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Требуется разрешение")
            .setMessage("Пожалуйста, предоставьте разрешение в настройках приложения")
            .setPositiveButton("Настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }

    private fun checkGoogleServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())

        if (result == ConnectionResult.SUCCESS) return true

        if (googleApiAvailability.isUserResolvableError(result)) {
            googleApiAvailability.getErrorDialog(
                this,
                result,
                REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR
            )?.show()
        } else {
            Toast.makeText(requireContext(), "Google сервисы недоступны", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun checkIfGpsEnabled(success: () -> Unit, error: (Exception) -> Unit) {
        LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(ActivityLocationService.locationRequest)
                    .build()
            )
            .addOnSuccessListener { success.invoke() }
            .addOnFailureListener { error.invoke(it) }
    }

    private fun performChecksAndStartService() {
        if (locationPermissionGranted && checkGoogleServicesAvailable()) {
            checkIfGpsEnabled(
                {
                    initProgressActivity()
                    showNewFragment()
                },
                {
                    if (it is ResolvableApiException) {
                        it.startResolutionForResult(
                            requireActivity(),
                            REQUEST_CODE_RESOLVE_GPS_ERROR
                        )
                    } else {
                        Toast.makeText(requireContext(), "GPS Недоступна", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
        }
    }
}