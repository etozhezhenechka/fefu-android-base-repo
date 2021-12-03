package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentMyActivityDetailsBinding
import ru.fefu.activitytracker.tracker.model.ActivityInfo

class MyActivityDetailsFragment : Fragment(R.layout.fragment_my_activity_details) {
    private var _binding: FragmentMyActivityDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "my_activity_details_fragment"

        fun newInstance(activityId: Int) : MyActivityDetailsFragment {
            val fragment = MyActivityDetailsFragment()

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
        _binding = FragmentMyActivityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myActivityToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val activityId = arguments?.getInt("activityId")
        val activityFromDB = activityId?.let { App.INSTANCE.db.activityDao().getById(it) }

        val activityInfo = activityFromDB?.let {
            ActivityInfo(it.id, it.distance, it.startTime, it.endTime, it.type)
        }

        binding.myActivityToolbar.title = activityInfo?.getType()
        binding.myActivityDistance.text = activityInfo?.getDistance()
        binding.myActivityTime.text = activityInfo?.getOffset()
        binding.myActivityDuration.text = activityInfo?.getDuration()
        binding.myActivityStartTime.text = activityInfo?.getStartTime()
        binding.myActivityFinishTime.text = activityInfo?.getEndTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}