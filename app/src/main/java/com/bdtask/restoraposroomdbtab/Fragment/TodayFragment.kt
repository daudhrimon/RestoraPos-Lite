package com.bdtask.restoraposroomdbtab.Fragment

import android.annotation.SuppressLint
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
import com.bdtask.restoraposroomdbtab.databinding.FragmentTodayBinding
import java.text.SimpleDateFormat
import java.util.*

class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private var todayList = mutableListOf<Order>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)

        MainActivity.database.orderDao()
            .getTodayOrder(SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),1)
            .observe(viewLifecycleOwner, Observer {

            todayList = it

            /*if (todayList.isNotEmpty()){
                binding.emptyOrder.visibility = View.GONE
                binding.rvLay.visibility = View.VISIBLE
                binding.todayRecycler.adapter = TodayAdapter(requireContext(),todayList)
            } else {*/
                binding.rvLay.visibility = View.GONE
                binding.emptyOrder.visibility = View.VISIBLE
            //}
        })

        binding.tdBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}