package ru.fefu.activitytracker.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentUserBinding

class UserFragment : Fragment(R.layout.fragment_user) {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "user_fragment"

        fun newInstance() : UserFragment {
            val fragment = UserFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePasswordProfileBtn.setOnClickListener {
            val activeFragment = parentFragmentManager.fragments.firstOrNull { !it.isHidden }

            parentFragmentManager.beginTransaction().apply {
                if (activeFragment != null) {
                    hide(activeFragment)
                }
                add(
                    R.id.fragment_view_profile,
                    ChangePasswordFragment.newInstance(),
                    ChangePasswordFragment.tag
                )
                addToBackStack(ChangePasswordFragment.tag)
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}