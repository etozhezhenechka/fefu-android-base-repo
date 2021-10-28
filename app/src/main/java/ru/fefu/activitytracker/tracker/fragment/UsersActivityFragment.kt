package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentUsersActivityBinding
import ru.fefu.activitytracker.tracker.adapter.MyActivityAdapter
import ru.fefu.activitytracker.tracker.adapter.UsersActivityAdapter
import ru.fefu.activitytracker.tracker.model.*
import java.time.LocalDateTime

class UsersActivityFragment : Fragment(R.layout.fragment_users_activity) {
    private var _binding: FragmentUsersActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var items: MutableList<CardItemModel>

    companion object {
        const val tag = "users_activity_fragment"

        fun newInstance() : UsersActivityFragment {
            val fragment = UsersActivityFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView = binding.userActivityRecyclerView
        recycleView.layoutManager = LinearLayoutManager(activity)

        fillList()

        recycleView.adapter = activity?.let { UsersActivityAdapter(items, it.supportFragmentManager) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillList() {
        val activitiesList = listOf(
            ActivityInfo(
                11.4,
                LocalDateTime.of(2021, 9, 27, 11, 22),
                LocalDateTime.of(2021, 9, 27, 12, 40),
                "Серфинг", "@etozhezhenechka"
            ),

            ActivityInfo(
                14.8,
                LocalDateTime.of(2021, 9, 26, 7, 40),
                LocalDateTime.of(2021, 9, 26, 10, 59),
                "Велосипед", "@etozhezhenechka_second"
            )
        )

        items = mutableListOf(
            DateLabelModel(FormattedDate(27, 9, 2021).toString())
        )

        for (item in activitiesList) {
            items.add(UsersActivityModel(item))
        }
    }
}