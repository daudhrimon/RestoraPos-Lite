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

class CompletedFragment : Fragment() {
    private lateinit var cBinding: FragmentCompletedBinding
    private var comList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cBinding = inflate(inflater, container, false)



        cBinding.tdBack.setOnClickListener {
            findNavController().popBackStack()
        }


        MainActivity.database.orderDao().getComOrder(1).observe(viewLifecycleOwner, Observer {

                comList = it.toMutableList()

                if (comList.isNotEmpty()){

                    cBinding.emptyOrder.visibility = View.GONE
                    cBinding.rvLay.visibility = View.VISIBLE
                    cBinding.todayRecycler.adapter = TodayAdapter(requireContext(),comList)
                } else {
                    cBinding.rvLay.visibility = View.GONE
                    cBinding.emptyOrder.visibility = View.VISIBLE
                }
            })



        return cBinding.root
    }
}