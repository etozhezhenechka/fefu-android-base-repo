package ru.fefu.activitytracker.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentActivityBinding
import ru.fefu.activitytracker.tracker.adapter.ActivityFragmentAdapter

class ActivityFragment : Fragment(R.layout.fragment_activity) {
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "activity_fragment"

        fun newInstance() : ActivityFragment {
            val fragment = ActivityFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pagerActivity.adapter = ActivityFragmentAdapter(this)

        TabLayoutMediator(binding.tabsActivity, binding.pagerActivity) { tab, position ->
            if (position == ActivityFragmentAdapter.firstPosition)
                tab.text = getString(R.string.pager_my_tab)
            else tab.text = getString(R.string.pager_users_tab)
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}