package com.bdtask.restoraposroomdbtab.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter
import com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding

class OngoingFragment : Fragment(), OngoingClickListener {
    private lateinit var ongBinding: FragmentOngoingBinding
    companion object { var ongoingList = mutableListOf<Order>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ongBinding = FragmentOngoingBinding.inflate(inflater, container, false)

        MainActivity.database.orderDao().getOngoing("Ongoing").observe(requireActivity(), Observer {
            ongoingList.clear()
            ongoingList = it.toMutableList()
            //orderList.asReversed()
            if (ongoingList.size > 0){
                ongBinding.ongoingRecycler.adapter = context?.let { OngoingAdapter(it, ongoingList,
                    this, ongBinding.ongHeader,ongBinding.searchBtn,ongBinding.crossBtn) }
            }
        })

        ongBinding.ongBack.setOnClickListener {
                /*for (i in ongoingList.indices) {
                     GlobalScope.launch {
                         MainActivity.database.orderDao().deleteOnGoing(Order(ongoingList[i].id,ongoingList[i].date,ongoingList[i].status,ongoingList[i].cartList,ongoingList[i].orderInfoList))
                     }
                 }*/
            findNavController().popBackStack()
        }

        ongBinding.searchBtn.setOnClickListener {
            if (ongBinding.ongSearchEt.visibility == View.GONE){
                ongBinding.ongHeader.visibility = View.GONE
                ongBinding.ongSearchEt.visibility = View.VISIBLE
                Util.showKeyboard(ongBinding.ongSearchEt)
                ongBinding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                ongBinding.ongSearchEt.visibility = View.GONE
                ongBinding.ongHeader.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), ongBinding.root)
                ongBinding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }

        ongBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), ongBinding.root)
        }

        return ongBinding.root
    }


    // from here we can handle onGoing Frags Buttons Visible or Gone

    @SuppressLint("NewApi")
    override fun onGoingItemClick(holder: OngoingAdapter.VHOngoing, position: Int, clickedList: ArrayList<Int>, multiSelect: Boolean) {
        if (multiSelect){
            when(clickedList.size){
                0 -> {

                }

                1 -> {

                }

                else -> {

                }
            }
        } else {

        }
    }
}