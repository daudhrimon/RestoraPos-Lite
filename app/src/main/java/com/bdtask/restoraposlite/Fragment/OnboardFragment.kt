package com.bdtask.restoraposlite.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bdtask.restoraposlite.Adapter.OnboardAdapter
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.FragmentOnboardBinding

class OnboardFragment : Fragment() {
    private lateinit var binding: FragmentOnboardBinding
    private var pos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardBinding.inflate(inflater, container, false)


        val images = intArrayOf(R.drawable.onb_one, R.drawable.onb_two, R.drawable.onb_three)


        binding.onbViewPager2.adapter = OnboardAdapter(requireContext(),images)


        addDots(0)


        binding.onbViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                addDots(binding.onbViewPager2.currentItem)
                pos = position
            }
        })


        binding.skipBtn.setOnClickListener {
            if (pos < 2) {
                binding.onbViewPager2.currentItem = pos+1
            } else {
                val sharedPref = SharedPref
                sharedPref.init(requireContext())
                //sharedPref.writeWelcome(1)
                findNavController().navigate(R.id.onboardFrag2settingFrag)
            }
        }


        return binding.root
    }


    private fun addDots(position: Int) {

        binding.dot1.setImageResource(R.drawable.dot_unselected)
        binding.dot2.setImageResource(R.drawable.dot_unselected)
        binding.dot3.setImageResource(R.drawable.dot_unselected)

        when (position) {

            0 -> {
                binding.dot1.setImageResource(R.drawable.dot_selected)
                binding.skipBtn.text = "SKIP"
            }

            1 -> {
                binding.dot2.setImageResource(R.drawable.dot_selected)
                binding.skipBtn.text = "SKIP"
            }

            2 -> {
                binding.dot3.setImageResource(R.drawable.dot_selected)
                binding.skipBtn.text = "DONE"
            }
        }
    }
}