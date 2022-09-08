package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Adapter.TodayAdapter
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.FragmentCompletedBinding.inflate
import com.bdtask.restoraposroomdbtab.databinding.FragmentCompletedBinding
import java.util.*

class CompletedFragment : Fragment() {
    private lateinit var binding: FragmentCompletedBinding
    private var comList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container, false)


        MainActivity.database.orderDao().getComOrder(1).observe(viewLifecycleOwner, Observer {

                comList = it.toMutableList()

                if (comList.isNotEmpty()){

                    binding.emptyOrder.visibility = View.GONE
                    binding.rvLay.visibility = View.VISIBLE
                    binding.todayRecycler.adapter = TodayAdapter(requireContext(),comList)
                } else {
                    binding.rvLay.visibility = View.GONE
                    binding.emptyOrder.visibility = View.VISIBLE
                }
            })


        binding.tdBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}