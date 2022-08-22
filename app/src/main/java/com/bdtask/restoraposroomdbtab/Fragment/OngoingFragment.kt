package com.bdtask.restoraposroomdbtab.Fragment

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
    private lateinit var binding: FragmentOngoingBinding
    companion object { var ongoingList = mutableListOf<Order>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOngoingBinding.inflate(inflater, container, false)

        MainActivity.database.orderDao().getOngoing("Ongoing").observe(requireActivity(), Observer {
            ongoingList.clear()
            ongoingList = it.toMutableList()
            //orderList.asReversed()
            if (ongoingList.size > 0){
                binding.ongoingRecycler.adapter = context?.let { OngoingAdapter(it, ongoingList,this) }
            }
        })

        binding.ongBack.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), binding.root)
            findNavController().popBackStack()
           /* for (i in ongoingList.indices) {
                GlobalScope.launch {
                    MainActivity.database.orderDao().deleteOnGoing(Order(ongoingList[i].id,ongoingList[i].date,ongoingList[i].status,ongoingList[i].cartList,ongoingList[i].orderInfoList))
                }
            }*/
        }

        binding.searchBtn.setOnClickListener {
            if (binding.ongSearchEt.visibility == View.GONE){
                binding.ongHeader.visibility = View.GONE
                binding.ongSearchEt.visibility = View.VISIBLE
                Util.showKeyboard(binding.ongSearchEt)
                binding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                binding.ongSearchEt.visibility = View.GONE
                binding.ongHeader.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), binding.root)
                binding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }

        binding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), binding.root)
        }

        return binding.root
    }


    // from here we can handle onGoing Frags Buttons Visible or Gone

    override fun onGoingItemClick(holder: OngoingAdapter.VHOngoing, position: Int, clickedList: MutableList<Int>) {

    }
}