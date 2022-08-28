package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {
    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayBinding.inflate(inflater, container, false)

        binding.tdBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}