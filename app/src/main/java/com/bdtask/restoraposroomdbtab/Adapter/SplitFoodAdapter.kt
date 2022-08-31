package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitItemBinding

class SplitFoodAdapter(private val context: Context,
                       private val cartList: MutableList<Cart>,
                       private val splitFoodClickListener: SplitFoodClickListener): RecyclerView.Adapter<SplitFoodAdapter.VHSplitFood>() {

    inner class VHSplitFood(binding: VhSplitItemBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitFood {
        return VHSplitFood(VhSplitItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_split_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitFood, position: Int) {
        holder.binding.spiTitle.text = cartList[position].title
        holder.binding.spiQnty.text = cartList[position].fQnty.toString()

        holder.itemView.setOnClickListener {
            if (cartList[position].fQnty > 0){
                var quantity = cartList[position].fQnty
                quantity -= 1
                cartList[position].fQnty = quantity
                //holder.binding.spiQnty.text = quantity.toString()
                splitFoodClickListener.onSplitFoodClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}