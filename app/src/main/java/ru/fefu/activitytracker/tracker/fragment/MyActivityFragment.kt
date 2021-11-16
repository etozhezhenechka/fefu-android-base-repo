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
import ru.fefu.activitytracker.tracker.adapter.MyActivityAdapter
import ru.fefu.activitytracker.tracker.model.*

class MyActivityFragment : Fragment(R.layout.fragment_my_activity) {
    private var _binding: FragmentMyActivityBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "my_activity_fragment"

        fun newInstance(): MyActivityFragment {
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

        recycleView.adapter = parentFragment?.let {
            MyActivityAdapter(mutableListOf(), it.parentFragmentManager)
        }

        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            val adapter = (recycleView.adapter as MyActivityAdapter)
            setDBItems(adapter, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDBItems(adapter: MyActivityAdapter, dbList: List<Activity>) {
        if (dbList.size == 1 && adapter.items.isEmpty()) disableWelcomeViews()

        if (dbList.isNotEmpty() && adapter.items.isNotEmpty()) {
            adapter.items.add(
                ActivityModel(
                    dbList.last().id,
                    ActivityInfo(
                        20.4,
                        dbList.last().startTime,
                        dbList.last().endTime,
                        dbList.last().type
                    )
                )
            )
            adapter.notifyItemInserted(adapter.items.size - 1)
        }

        if (dbList.isNotEmpty() && adapter.items.isEmpty()) {
            disableWelcomeViews()

            for (item in dbList) {
                adapter.items.add(
                    ActivityModel(
                        item.id,
                        ActivityInfo(20.4, item.startTime, item.endTime, item.type)
                    )
                )
                adapter.notifyItemInserted(adapter.items.size - 1)
            }
        }

        if (dbList.size < adapter.items.size) {
            for (item in adapter.items) {
                val itemInDB = dbList.filter { it.id == item.id }

                if (itemInDB.isNotEmpty()) continue
                else adapter.items.remove(item)
            }
        }
    }

    private fun disableWelcomeViews() {
        binding.welcomeText.visibility = View.GONE
        binding.welcomeSubText.visibility = View.GONE
    }
}