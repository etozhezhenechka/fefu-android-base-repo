package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentUsersActivityDetailsBinding
import ru.fefu.activitytracker.tracker.model.ActivityInfo

class UsersActivityDetailsFragment : Fragment(R.layout.fragment_users_activity_details) {
    private var _binding: FragmentUsersActivityDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "users_activity_details_fragment"

        fun newInstance(activityId: Int) : UsersActivityDetailsFragment {
            val fragment = UsersActivityDetailsFragment()

            val args = Bundle()
            args.putInt("activityId", activityId)
            fragment.arguments = args

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersActivityToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val activityId = arguments?.getInt("activityId")
        val activityFromDB = activityId?.let { App.INSTANCE.db.activityDao().getById(it) }

        val activityInfo = activityFromDB?.let {
            ActivityInfo(it.id, it.distance, it.startTime, it.endTime, it.type)
        }

        binding.usersActivityToolbar.title = activityInfo?.getType()
        binding.usersActivityUsername.text = activityInfo?.getUsername()
        binding.usersActivityDistance.text = activityInfo?.getDistance()
        binding.usersActivityTime.text = activityInfo?.getOffset()
        binding.usersActivityDuration.text = activityInfo?.getDuration()
        binding.usersActivityStartTime.text = activityInfo?.getStartTime()
        binding.usersActivityFinishTime.text = activityInfo?.getEndTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}