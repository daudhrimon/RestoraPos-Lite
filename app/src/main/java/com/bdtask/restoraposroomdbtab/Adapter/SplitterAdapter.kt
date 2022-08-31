package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Dialog.SplitOrder.Companion.splitterIndex
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Split
import com.bdtask.restoraposroomdbtab.databinding.VhSplitterBinding

class SplitterAdapter(private val context: Context,
                      private val splitterList: MutableList<Split> ): RecyclerView.Adapter<SplitterAdapter.VHSplitter>() {

    inner class VHSplitter(binding: VhSplitterBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitter {
        return VHSplitter(VhSplitterBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitter, position: Int) {

        holder.binding.orderId.text = "Order Id : " + splitterList[position].id

        if (splitterIndex == position){
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_selected)
        } else {
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_unselect)
        }

        holder.binding.splitterDetRv.adapter = SplitterDetailsAdapter(context, splitterList[position].cart)

        holder.binding.splitter.setOnClickListener {
            if (splitterIndex == position){
                splitterIndex = -1
            } else {
                splitterIndex = position
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return splitterList.size
    }
}