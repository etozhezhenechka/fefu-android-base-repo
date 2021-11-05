package ru.fefu.activitytracker.newactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentStartNewActivityBinding

class StartNewActivityFragment : Fragment(R.layout.fragment_start_new_activity) {
    private var _binding: FragmentStartNewActivityBinding? = null
    private val binding get() = _binding!!

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}