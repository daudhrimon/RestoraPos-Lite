package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class OngoingFragment : Fragment(), OngoingClickListener {
    private lateinit var ongBinding: FragmentOngoingBinding
    companion object { var ongList = mutableListOf<Order>() }
    private var position = -1
    private var selectedItem = -1
    var clickedList = arrayListOf<Int>()
    private val status = "Ongoing"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ongBinding = FragmentOngoingBinding.inflate(inflater, container, false)

        MainActivity.database.orderDao().getOngoing(status).observe(requireActivity(), Observer {
            ongList.clear()
            ongList = it.toMutableList()
            if (ongList.size > 0){
                ongBinding.ongRecycler.adapter = context?.let { OngoingAdapter(it, ongList,
                    this, ongBinding.ongHeader,ongBinding.searchBtn,ongBinding.tickBtn,ongBinding.scrollView) }
            } else {
                ongBinding.ongRecycler.visibility = View.GONE
            }
            ongBinding.scrollView.visibility = View.GONE
            ongBinding.tickBtn.visibility = View.GONE
            ongBinding.ongHeader.text = "Ongoing Order"
            ongBinding.searchBtn.visibility = View.VISIBLE
            Log.wtf("A bodda iam alive","But aske amar mon valo nei : "+ ongList.size)
        })

        ongBinding.ongBack.setOnClickListener {
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

        ongBinding.cancelBtn.setOnClickListener {
            if (selectedItem == 1){
                GlobalScope.launch {
                    MainActivity.database.orderDao().deleteOnGoing(Order(ongList[position].id,status,
                        ongList[position].date, ongList[position].token,ongList[position].cartList,
                        ongList[position].orderInfoList))
                }
                context?.let { it1 -> Toasty.success(it1,"Selected Item Deleted",Toasty.LENGTH_SHORT).show() }
            } else {
                for (i in clickedList.indices){
                    GlobalScope.launch {
                        MainActivity.database.orderDao().deleteOnGoing(Order(ongList[i].id,status,
                            ongList[i].date, ongList[i].token,ongList[i].cartList,
                            ongList[i].orderInfoList))
                    }
                }
                context?.let { it1 -> Toasty.success(it1,"Selected Items Deleted",Toasty.LENGTH_SHORT).show() }
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////
        //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
        //////////////////////////////////////////////////////////////////////////////////////////

        return ongBinding.root
    }


    // from here we can handle onGoing Fragment's Buttons Visible or Gone from Adapter Class

    override fun onGoingItemClick(position: Int, clickedList: ArrayList<Int>, selectedItem: Int) {
        this.position = position
        this.clickedList = clickedList
        this.selectedItem = selectedItem

        if (selectedItem == 1) {
            ongBinding.mergeBtn.visibility = View.GONE
            ongBinding.cancelBtn.visibility = View.VISIBLE
            ongBinding.splitBtn.visibility = View.VISIBLE
            ongBinding.dueposBtn.visibility = View.VISIBLE
            ongBinding.tokenBtn.visibility = View.VISIBLE
            ongBinding.editBtn.visibility = View.VISIBLE
            ongBinding.completeBtn.visibility = View.VISIBLE
            ongBinding.scrollView.post {
                ongBinding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)
            }
        } else {
            ongBinding.splitBtn.visibility = View.GONE
            ongBinding.dueposBtn.visibility = View.GONE
            ongBinding.tokenBtn.visibility = View.GONE
            ongBinding.editBtn.visibility = View.GONE
            ongBinding.completeBtn.visibility = View.GONE
            ongBinding.cancelBtn.visibility = View.VISIBLE
            ongBinding.mergeBtn.visibility = View.VISIBLE
        }
        Log.wtf("Abodda Fragment a Asi", this.clickedList.toString())
    }
}