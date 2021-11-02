package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentMyActivityDetailsBinding

class MyActivityDetailsFragment : Fragment(R.layout.fragment_my_activity_details) {
    private var _binding: FragmentMyActivityDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "my_activity_details_fragment"

        fun newInstance() : MyActivityDetailsFragment {
            val fragment = MyActivityDetailsFragment()
            fragment.arguments = Bundle()
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
            parentFragmentManager.beginTransaction().apply {
                val activeFragment = parentFragmentManager.findFragmentByTag(MyActivityDetailsFragment.tag)
                if (activeFragment != null) {
                    hide(activeFragment)
                }
                val hiddenFragment = parentFragmentManager.findFragmentByTag(RecyclerViewFragment.tag)
                if (hiddenFragment != null) {
                    show(hiddenFragment)
                }
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}