package com.bdtask.restoraposlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bdtask.restoraposlite.adapters.OnboardAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.databinding.FragmentOnboardBinding

class OnboardFragment : Fragment() {
    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!
    private var pos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val images = intArrayOf(R.drawable.onb_one, R.drawable.onb_two, R.drawable.onb_three)
        val texts = intArrayOf(R.string.onb_one, R.string.onb_two, R.string.onb_three)

        binding.onbViewPager2.adapter = OnboardAdapter(requireContext(), images, texts)

        addDots(0)


        binding.onbViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                addDots(binding.onbViewPager2.currentItem)
                pos = position
            }
        })


        binding.skipBtn.setOnClickListener {
            if (pos < 2) {
                binding.onbViewPager2.currentItem = pos + 1
            } else {
                val sharedPref = SharedPref
                sharedPref.init(requireContext())
                findNavController().navigate(R.id.onboardFrag2settingFrag)
            }
        }


        return binding.root
    }


    private fun addDots(position: Int) {

        when (position) {

            0 -> {
                binding.dot1.setImageResource(R.drawable.dot_selected)
                binding.dot2.setImageResource(R.drawable.dot_unselected)
                binding.dot3.setImageResource(R.drawable.dot_unselected)
                binding.skipBtn.text = "SKIP"
            }

            1 -> {
                binding.dot2.setImageResource(R.drawable.dot_selected)
                binding.dot1.setImageResource(R.drawable.dot_unselected)
                binding.dot3.setImageResource(R.drawable.dot_unselected)
                binding.skipBtn.text = "SKIP"
            }

            2 -> {
                binding.dot3.setImageResource(R.drawable.dot_selected)
                binding.dot1.setImageResource(R.drawable.dot_unselected)
                binding.dot2.setImageResource(R.drawable.dot_unselected)
                binding.skipBtn.text = "DONE"
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}