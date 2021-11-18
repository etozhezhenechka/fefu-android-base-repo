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
    private val dateHandler = DateActivityHandler()

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
        if (dbList.isEmpty()) enableWelcomeViews()
        else disableWelcomeViews()

        for (item in dbList) dateHandler.addItem(item)

        adapter.items = dateHandler.getCardList()
        adapter.notifyDataSetChanged()
    }

    private fun disableWelcomeViews() {
        binding.welcomeText.visibility = View.GONE
        binding.welcomeSubText.visibility = View.GONE
    }

    private fun enableWelcomeViews() {
        binding.welcomeText.visibility = View.VISIBLE
        binding.welcomeSubText.visibility = View.VISIBLE
    }
}