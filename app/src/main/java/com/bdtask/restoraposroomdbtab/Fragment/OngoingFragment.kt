package com.bdtask.restoraposroomdbtab.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter
import com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class OngoingFragment : Fragment(), OngoingClickListener {
    private lateinit var ongBinding: FragmentOngoingBinding
    companion object {
        var ongList = mutableListOf<Order>()
        var clickedList = arrayListOf<Int>()
        var multiSelect = false
    }
    private var position = -1
    private var selectedItem = -1
    private val status = "Ongoing"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ongBinding = FragmentOngoingBinding.inflate(inflater, container, false)

        MainActivity.database.orderDao().getOngoing(status).observe(viewLifecycleOwner, Observer {
            ongList.clear()
            ongList = it.toMutableList()

            clickedList.clear()
            ongBinding.scrollView.visibility = View.GONE
            ongBinding.tickBtn.visibility = View.GONE
            ongBinding.ongHeader.text = "Ongoing Order"
            ongBinding.searchBtn.visibility = View.VISIBLE

            if (ongList.isNotEmpty()){
                ongBinding.ongRecycler.adapter = OngoingAdapter(requireContext(), ongList,this)
            } else {
                ongBinding.ongRecycler.visibility = View.GONE
            }
            Log.wtf("A bodda iam alive","But aske amar mon valo nei : "+ ongList.size)
        })

        ongBinding.ongBack.setOnClickListener {
            if (multiSelect){
                disableMultiSelect()
            } else {
                findNavController().popBackStack()
            }
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

        ongBinding.root.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && multiSelect) {
                disableMultiSelect()
                return@OnKeyListener true
            }
            false
        })

        ongBinding.tickBtn.setOnClickListener {
            disableMultiSelect()
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        ongBinding.cancelBtn.setOnClickListener {
            if (selectedItem == 1){
                GlobalScope.launch {
                    MainActivity.database.orderDao().deleteOnGoing(Order(ongList[position].id,status,
                        ongList[position].date, ongList[position].token,ongList[position].cartList,
                        ongList[position].orderInfoList))
                }
                Toasty.success(requireContext(),"Selected Item Deleted",Toasty.LENGTH_SHORT).show()
            } else {
                for (i in clickedList.indices){
                    val pos = clickedList[i]
                    GlobalScope.launch {
                        MainActivity.database.orderDao().deleteOnGoing(Order(ongList[pos].id,status,
                            ongList[pos].date, ongList[pos].token,ongList[pos].cartList,
                            ongList[pos].orderInfoList))
                    }
                }
                Toasty.success(requireContext(),"Selected Items Deleted",Toasty.LENGTH_SHORT).show()
            }
            if (multiSelect){
                multiSelect = false
                clickedList.clear()
                ongBinding.root.isFocusableInTouchMode = false
                ongBinding.root.clearFocus()
            }
        }

        ongBinding.splitBtn.setOnClickListener {
           splitOrderHandler()
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        return ongBinding.root
    }

    private fun splitOrderHandler() {
        val dialog = Dialog(requireContext())
        //val spBinding =
    }


    // from here we can handle onGoing Fragment's Buttons Visible or Gone from Adapter Class

    @SuppressLint("NotifyDataSetChanged")
    override fun onGoingItemClick(position: Int, selectedItem: Int) {
        this.position = position
        this.selectedItem = selectedItem

        if (multiSelect){
            ongBinding.searchBtn.visibility = View.GONE
            ongBinding.tickBtn.visibility = View.VISIBLE
            ongBinding.ongHeader.text = clickedList.size.toString() + " / " + ongList.size.toString() + " Selected"
            ongBinding.root.isFocusableInTouchMode = true
            ongBinding.root.requestFocus()
        }

        when (selectedItem) {
            0 -> {
                ongBinding.scrollView.visibility = View.GONE
            }

            1 -> {
                ongBinding.scrollView.visibility = View.VISIBLE
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
            }

            else -> {
                ongBinding.scrollView.visibility = View.VISIBLE
                ongBinding.splitBtn.visibility = View.GONE
                ongBinding.dueposBtn.visibility = View.GONE
                ongBinding.tokenBtn.visibility = View.GONE
                ongBinding.editBtn.visibility = View.GONE
                ongBinding.completeBtn.visibility = View.GONE
                ongBinding.cancelBtn.visibility = View.VISIBLE
                ongBinding.mergeBtn.visibility = View.VISIBLE
            }
        }
        ongBinding.ongRecycler.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun disableMultiSelect() {
        ongBinding.scrollView.visibility = View.GONE
        multiSelect = false
        clickedList.clear()
        ongBinding.tickBtn.visibility = View.GONE
        ongBinding.ongHeader.text = "Ongoing Order"
        ongBinding.searchBtn.visibility = View.VISIBLE
        ongBinding.ongRecycler.adapter?.notifyDataSetChanged()
        ongBinding.root.isFocusableInTouchMode = false
        ongBinding.root.clearFocus()
    }
}
