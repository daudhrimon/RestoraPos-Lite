package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Adapter.ViewOrderAdapter
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.database
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.FragmentCompletedBinding

class CompletedFragment : Fragment() {
    private lateinit var cBinding: FragmentCompletedBinding
    private var comList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        cBinding = FragmentCompletedBinding.inflate(inflater, container, false)


        cBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        cBinding.searchBtn.setOnClickListener {
            if (cBinding.searchEt.visibility == View.GONE){
                cBinding.header.visibility = View.GONE
                cBinding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(cBinding.searchEt)
                cBinding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                cBinding.searchEt.visibility = View.GONE
                cBinding.header.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), cBinding.root)
                cBinding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }



        database.orderDao().getCOrder(1).observe(viewLifecycleOwner, Observer {

                comList = it.toMutableList()

                if (comList.isNotEmpty()){

                    cBinding.emptyOrder.visibility = View.GONE
                    cBinding.rvLay.visibility = View.VISIBLE
                    cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),comList)
                } else {
                    cBinding.rvLay.visibility = View.GONE
                    cBinding.emptyOrder.visibility = View.VISIBLE
                }
            })



        return cBinding.root
    }
}