package com.bdtask.restoraposroomdbtab.Fragment

import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ScrollView
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
            if (ongoingList.size > 0){
                ongBinding.ongoingRecycler.adapter = context?.let { OngoingAdapter(it, ongoingList,
                    this, ongBinding.ongHeader,ongBinding.searchBtn,ongBinding.crossBtn,ongBinding.scrlView) }
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
    override fun onGoingItemClick(position: Int, clickedList: ArrayList<Int>, selectedItem: Int) {
        if (selectedItem == 1) {
            ongBinding.cancelBtn.visibility = View.VISIBLE
            ongBinding.mergeBtn.visibility = View.GONE
            ongBinding.splitBtn.visibility = View.VISIBLE
            ongBinding.dueposBtn.visibility = View.VISIBLE
            ongBinding.tokenBtn.visibility = View.VISIBLE
            ongBinding.editBtn.visibility = View.VISIBLE
            ongBinding.completeBtn.visibility = View.VISIBLE
            ongBinding.scrlView.post {
                ongBinding.scrlView.fullScroll(ScrollView.FOCUS_RIGHT)
            }
        } else {
            ongBinding.cancelBtn.visibility = View.VISIBLE
            ongBinding.mergeBtn.visibility = View.VISIBLE
            ongBinding.splitBtn.visibility = View.GONE
            ongBinding.dueposBtn.visibility = View.GONE
            ongBinding.tokenBtn.visibility = View.GONE
            ongBinding.editBtn.visibility = View.GONE
            ongBinding.completeBtn.visibility = View.GONE
        }
    }
}