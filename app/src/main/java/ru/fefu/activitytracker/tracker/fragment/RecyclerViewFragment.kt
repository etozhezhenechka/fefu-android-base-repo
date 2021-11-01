package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentRecyclerViewBinding
import ru.fefu.activitytracker.tracker.adapter.ViewPagerAdapter

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "recycler_view_fragment"

        fun newInstance() : RecyclerViewFragment {
            val fragment = RecyclerViewFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pagerActivity.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabsActivity, binding.pagerActivity) { tab, position ->
            if (position == ViewPagerAdapter.firstPosition)
                tab.text = getString(R.string.pager_my_tab)
            else tab.text = getString(R.string.pager_users_tab)
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}