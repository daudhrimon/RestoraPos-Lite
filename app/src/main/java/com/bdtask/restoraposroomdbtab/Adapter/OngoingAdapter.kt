package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
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
    private val ongoingClickListener: OngoingClickListener,
    private val ongHeader: TextView,
    private val searchBtn: ImageView,
    private val tickBtn: ImageView,
    private val scrollView: HorizontalScrollView ): RecyclerView.Adapter<OngoingAdapter.VHOngoing>() {

    var index = -1

    inner class VHOngoing(binding: VhOngoingBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOngoing {
        return VHOngoing(VhOngoingBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_ongoing, parent, false)))
    }


    override fun onBindViewHolder(holder: VHOngoing, position: Int) {
        holder.binding.cusName.text = "Customer : " + ongList[position].orderInfoList[0].customerInfo[0].cusName
        holder.binding.orderId.text = "Order Id : " + ongList[position].id.toString()
        if (ongList[position].orderInfoList[0].waiter.isNotEmpty()) {
            holder.binding.waiterName.visibility = View.VISIBLE
            holder.binding.waiterName.text = "Waiter : " + ongList[position].orderInfoList[0].waiter
        } else {
            holder.binding.waiterName.visibility = View.INVISIBLE
        }
        if (ongList[position].orderInfoList[0].table.isNotEmpty()) {
            holder.binding.tableName.text = "Table : " + ongList[position].orderInfoList[0].table
        } else {
            holder.binding.tableName.text = ongList[position].orderInfoList[0].customerType
        }

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
                if (clickedList.contains(position)) {
                    clickedList.remove(position)
                } else {
                    clickedList.add(position)
                }
                ongHeader.text = clickedList.size.toString() + " / " + ongList.size.toString() + " Selected"

                if (clickedList.size > 0){
                    scrollView.visibility = View.VISIBLE
                    ongoingClickListener.onGoingItemClick(position, clickedList.size)
                } else {
                    scrollView.visibility = View.GONE
                }
            } else {
                if (index == position) {
                    index = -1
                    clickedList.clear()
                    scrollView.visibility = View.GONE
                } else {
                    index = position
                    scrollView.visibility = View.VISIBLE
                    ongoingClickListener.onGoingItemClick(position,1)
                }
            }
            notifyDataSetChanged()
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

                searchBtn.visibility = View.GONE
                ongHeader.text = clickedList.size.toString() + " / " + ongList.size.toString() + " Selected"
                tickBtn.visibility = View.VISIBLE
            }
            scrollView.visibility = View.VISIBLE
            ongoingClickListener.onGoingItemClick(position,clickedList.size)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }

        tickBtn.setOnClickListener {
            tickBtn.visibility = View.GONE
            multiSelect = false
            clickedList.clear()

            ongHeader.text = "Ongoing Order"
            searchBtn.visibility = View.VISIBLE
            scrollView.visibility = View.GONE

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return ongList.size
    }
}