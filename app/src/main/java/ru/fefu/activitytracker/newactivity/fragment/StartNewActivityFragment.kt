package ru.fefu.activitytracker.newactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.database.Activity
import ru.fefu.activitytracker.databinding.FragmentStartNewActivityBinding
import ru.fefu.activitytracker.newactivity.adapter.ActivityTypeAdapter
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel
import ru.fefu.activitytracker.newactivity.model.ActivityType
import ru.fefu.activitytracker.newactivity.selectiontracker.CardDetailsLookup
import ru.fefu.activitytracker.newactivity.selectiontracker.CardPredicate
import ru.fefu.activitytracker.newactivity.service.ActivityForegroundService
import java.time.LocalDateTime

class StartNewActivityFragment : Fragment(R.layout.fragment_start_new_activity) {
    private var _binding: FragmentStartNewActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var items: MutableList<ActivityTypeModel>

    companion object {
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

        fillList()

        val typeAdapter = ActivityTypeAdapter(items)
        typeAdapter.setHasStableIds(true)

        recycleView.adapter = typeAdapter

        val selectionTracker = SelectionTracker.Builder(
            "selection-id", recycleView,
            StableIdKeyProvider(recycleView), CardDetailsLookup(recycleView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(CardPredicate()).build()

        selectionTracker.select(ActivityType.BIKING.ordinal.toLong())

        (recycleView.adapter as ActivityTypeAdapter).tracker = selectionTracker

        binding.activityStartBtn.setOnClickListener {
            ActivityForegroundService.startForeground(requireContext(), 111)

            App.INSTANCE.db.activityDao().insert(
                Activity(
                    0,
                    ActivityType.values()[selectionTracker.selection.first().toInt()],
                    LocalDateTime.of(2021, 1, 24, 12, 20),
                    LocalDateTime.of(2021, 1, 24, 13, 41),
                    listOf(Pair(1.0, 1.1), Pair(1.1, 1.0))
                )
            )

            showNewFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillList() {
        items = mutableListOf(
            ActivityTypeModel(ActivityType.BIKING), ActivityTypeModel(ActivityType.RUNNING),
            ActivityTypeModel(ActivityType.SWIMMING)
        )
    }

    private fun showNewFragment() {
        val activeFragment = parentFragmentManager.fragments.firstOrNull { !it.isHidden }

        parentFragmentManager.beginTransaction().apply {
            if (activeFragment != null) hide(activeFragment)

            add(
                R.id.fragment_view_menu_new_activity,
                ProgressNewActivityFragment.newInstance(),
                ProgressNewActivityFragment.tag
            )

            addToBackStack(ProgressNewActivityFragment.tag)

            commit()
        }
    }
}