package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding
import com.google.gson.Gson

class OngoingAdapter(
    private val context: Context,
    private var orderList: MutableList<Order>,
    private val ongoingClickListener: OngoingClickListener): RecyclerView.Adapter<OngoingAdapter.VHOngoing>() {

    var clickedList = mutableListOf<Int>()

    inner class VHOngoing(binding: VhOngoingBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOngoing {
        return VHOngoing(VhOngoingBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_ongoing, parent, false)))
    }

    override fun onBindViewHolder(holder: VHOngoing, position: Int) {
        holder.binding.cusName.text =
            "Customer : " + orderList[position].orderInfoList[0].customerInfo[0].cusName

        holder.binding.orderId.text = "Order Id : " + orderList[position].id.toString()

        if (orderList[position].orderInfoList[0].waiter.isNotEmpty()) {
            holder.binding.waiterName.visibility = View.VISIBLE
            holder.binding.waiterName.text =
                "Waiter : " + orderList[position].orderInfoList[0].waiter
        } else {
            holder.binding.waiterName.visibility = View.INVISIBLE
        }
        if (orderList[position].orderInfoList[0].table.isNotEmpty()) {
            holder.binding.tableName.text = "Table : " + orderList[position].orderInfoList[0].table
        } else {
            holder.binding.tableName.text = orderList[position].orderInfoList[0].customerType
        }

        ///////////////////////////////////////
        // setting Selected Background Color //
        ///////////////////////////////////////

       if (clickedList.contains(position)) {
            holder.binding.table.setImageResource(R.drawable.selected_ongoing)
        } else {
            holder.binding.table.setImageResource(R.drawable.unselected_ongoing)
        }

        holder.itemView.setOnClickListener {
            if (clickedList.contains(position)) {
                clickedList.remove(position)
            } else {
                clickedList.add(position)
            }
            notifyDataSetChanged()
            Log.wtf("OnGoingClick",clickedList.toString())
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}