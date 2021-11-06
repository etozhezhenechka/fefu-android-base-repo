package ru.fefu.activitytracker.newactivity.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentStartNewActivityBinding
import ru.fefu.activitytracker.newactivity.adapter.ActivityTypeAdapter
import ru.fefu.activitytracker.newactivity.model.ActivityTypeModel

class StartNewActivityFragment : Fragment(R.layout.fragment_start_new_activity) {
    private var _binding: FragmentStartNewActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var items: MutableList<ActivityTypeModel>

    companion object {
        const val tag = "start_new_activity_fragment"

        fun newInstance() : StartNewActivityFragment {
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

        recycleView.adapter = ActivityTypeAdapter(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillList() {
        items = mutableListOf(ActivityTypeModel("Велосипед"), ActivityTypeModel("Бег"),
            ActivityTypeModel("Плавание"))
    }
}