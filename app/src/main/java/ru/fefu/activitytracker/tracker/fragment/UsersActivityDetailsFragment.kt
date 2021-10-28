package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentUsersActivityDetailsBinding

class UsersActivityDetailsFragment : Fragment(R.layout.fragment_users_activity_details) {
    private var _binding: FragmentUsersActivityDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "users_activity_details_fragment"

        fun newInstance() : UsersActivityDetailsFragment {
            val fragment = UsersActivityDetailsFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersActivityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}