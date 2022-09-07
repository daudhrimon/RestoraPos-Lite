package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.VhTodayOrderBinding

class TodayAdapter(private val context: Context,
                   private val todayList: MutableList<Order>): RecyclerView.Adapter<TodayAdapter.VHToday>() {
    var index = -1

    inner class VHToday(_binding: VhTodayOrderBinding): RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHToday {
        return VHToday(VhTodayOrderBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_today_order,parent,false)))
    }

    override fun onBindViewHolder(holder: VHToday, position: Int) {

        if (index == position){
            holder.binding.expandLay.visibility = View.VISIBLE
        } else {
            holder.binding.expandLay.visibility = View.GONE
        }

        holder.binding.parentLay.setOnClickListener {
            index = if (index == position){
                holder.binding.expandLay.visibility = View.GONE
                -1
            } else {
                holder.binding.expandLay.visibility = View.VISIBLE
                position
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }
}