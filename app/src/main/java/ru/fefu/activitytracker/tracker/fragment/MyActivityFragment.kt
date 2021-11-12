package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.database.Activity
import ru.fefu.activitytracker.databinding.FragmentMyActivityBinding
import ru.fefu.activitytracker.newactivity.model.ActivityType
import ru.fefu.activitytracker.tracker.adapter.MyActivityAdapter
import ru.fefu.activitytracker.tracker.model.*
import java.time.LocalDateTime

class MyActivityFragment : Fragment(R.layout.fragment_my_activity) {
    private var _binding: FragmentMyActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var items: MutableList<CardItemModel>
    private val hardcodedItemCount = 3

    companion object {
        const val tag = "my_activity_fragment"

        fun newInstance() : MyActivityFragment {
            val fragment = MyActivityFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView = binding.myActivityRecyclerView
        recycleView.layoutManager = LinearLayoutManager(activity)

        fillList()

        recycleView.adapter = parentFragment?.let { MyActivityAdapter(items, it.parentFragmentManager) }

        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            val adapter = (recycleView.adapter as MyActivityAdapter)
            addDBItems(adapter, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillList() {
        val activitiesList = listOf(
            ActivityInfo(
                11.4,
                LocalDateTime.of(2021, 10, 27, 11, 22),
                LocalDateTime.of(2021, 10, 27, 12, 40),
                ActivityType.SWIMMING
            ),

            ActivityInfo(
                14.8,
                LocalDateTime.of(2021, 10, 27, 7, 40),
                LocalDateTime.of(2021, 10, 27, 10, 59),
                ActivityType.BIKING
            )
        )

        items = mutableListOf(
            DateLabelModel(FormattedDate(27, 10, 2021).toString())
        )

        for (item in activitiesList) {
            items.add(ActivityModel(item))
        }
    }

    private fun addDBItems(adapter: MyActivityAdapter, dbList: List<Activity>) {
        if (dbList.isNotEmpty() && adapter.items.size > hardcodedItemCount) {
            adapter.items.add(
                ActivityModel(ActivityInfo(
                    20.4,
                    dbList.last().startTime,
                    dbList.last().endTime,
                    dbList.last().type
                ))
            )
            adapter.notifyItemInserted(adapter.items.size - 1)
        }

        if (dbList.isNotEmpty() && adapter.items.size == hardcodedItemCount) {
            for (item in dbList) {
                adapter.items.add(
                    ActivityModel(ActivityInfo(20.4, item.startTime, item.endTime, item.type))
                )
                adapter.notifyItemInserted(adapter.items.size - 1)
            }
        }
    }
}