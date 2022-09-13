/*
package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Interface.SplitFoodClickListener
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhSplitItemBinding

class SplitFoodAdapter( private val context: Context,
                        private val cart: MutableList<Cart>,
                        private val splitFoodClickListener: SplitFoodClickListener ): RecyclerView.Adapter<SplitFoodAdapter.VHSplitFood>() {

    inner class VHSplitFood(binding: VhSplitItemBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitFood {
        return VHSplitFood(VhSplitItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_split_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitFood, position: Int) {

        holder.binding.spiTitle.text = cart[position].title

        holder.binding.spiQnty.text = cart[position].fQnty.toString()



        holder.itemView.setOnClickListener {

            splitFoodClickListener.onSplitFoodClick(position)

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}*/
