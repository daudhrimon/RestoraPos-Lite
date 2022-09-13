package com.bdtask.restoraposlite.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.databinding.FragmentAboutusBinding

class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutusBinding.inflate(inflater, container, false)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }
}