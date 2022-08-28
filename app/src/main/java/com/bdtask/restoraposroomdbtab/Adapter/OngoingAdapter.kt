package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion.clickedList
import com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion.multiSelect
import com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding

class OngoingAdapter(
    private val context: Context,
    private var ongList: MutableList<Order>,
    private val ongoingClickListener: OngoingClickListener ): RecyclerView.Adapter<OngoingAdapter.VHOngoing>() {

    var index = -1

    inner class VHOngoing(binding: VhOngoingBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOngoing {
        return VHOngoing(VhOngoingBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_ongoing, parent, false)))
    }


    override fun onBindViewHolder(holder: VHOngoing, position: Int) {
        try {
            holder.binding.cusName.text = "Customer : " + ongList[position].orderInfoList[0].customerInfo[0].cusName
        } catch (e: Exception){/**/}

        try {
            holder.binding.orderId.text = "Order Id : " + ongList[position].id.toString()
        } catch (e: Exception){/**/}

        try {
            if (ongList[position].orderInfoList[0].waiter.isNotEmpty()) {
                holder.binding.waiterName.visibility = View.VISIBLE
                holder.binding.waiterName.text = "Waiter : " + ongList[position].orderInfoList[0].waiter
            } else {
                holder.binding.waiterName.visibility = View.INVISIBLE
            }
        } catch (e: Exception){/**/}

        try {
            if (ongList[position].orderInfoList[0].table.isNotEmpty()) {
                holder.binding.tableName.text = "Table : " + ongList[position].orderInfoList[0].table
            } else {
                if (ongList[position].orderInfoList[0].customerType.isNotEmpty()){
                    holder.binding.tableName.text = ongList[position].orderInfoList[0].customerType
                }
            }
        } catch (e: Exception){/**/}

        ///////////////////////////////////////
        // setting Selected Background Color //
        ///////////////////////////////////////

       if (multiSelect){
            if (clickedList.contains(position)) {
                holder.binding.table.setImageResource(R.drawable.selected_ongoing)
            } else {
                holder.binding.table.setImageResource(R.drawable.unselected_ongoing)
            }
        } else {
            if (index == position){
                holder.binding.table.setImageResource(R.drawable.selected_ongoing)
            } else {
                holder.binding.table.setImageResource(R.drawable.unselected_ongoing)
            }
        }

        holder.itemView.setOnClickListener {
            if (multiSelect){
                if (clickedList.isNotEmpty() && clickedList.contains(position)) {
                    clickedList.remove(position)
                } else {
                    clickedList.add(position)
                }
                ongoingClickListener.onGoingItemClick(position, clickedList.size)
            } else {
                // just single click
                var count = 0
                if (index == position) {
                    index = -1
                    count = 0
                } else {
                    index = position
                    count = 1
                }
                clickedList.clear()
                ongoingClickListener.onGoingItemClick(position,count)
            }
            Log.wtf("Abodda adapter a o asi, proman ase hahaha",clickedList.toString())
        }

        holder.itemView.setOnLongClickListener {
            if (clickedList.contains(position)) {
                clickedList.remove(position)
            } else {
                clickedList.add(position)
            }
            if (!multiSelect){
                multiSelect = true
                index = -1
            }
            ongoingClickListener.onGoingItemClick(position,clickedList.size)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return ongList.size
    }
}