package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitItemBinding

class SplitFoodAdapter(private val context: Context,
                       private val cartList: List<FoodCart>,
                       private val splitFoodClickListener: SplitFoodClickListener): RecyclerView.Adapter<SplitFoodAdapter.VHSplitFood>() {

    inner class VHSplitFood(binding: VhSplitItemBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitFood {
        return VHSplitFood(VhSplitItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_split_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitFood, position: Int) {
        holder.binding.spiTitle.text = cartList[position].foodTitle
        holder.binding.spiQnty.text = cartList[position].foodQuantity.toString()

        holder.itemView.setOnClickListener {
            if (cartList[position].foodQuantity > 0){
                var quantity = cartList[position].foodQuantity
                quantity -= 1
                holder.binding.spiQnty.text = quantity.toString()
                splitFoodClickListener.onSplitFoodClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}