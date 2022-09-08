package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhViewOrderItemBinding

class ViewOrderItemAdapter(val context: Context,
                           private val cart:MutableList<Cart>): RecyclerView.Adapter<ViewOrderItemAdapter.VHViewOrderItem>() {

    inner class VHViewOrderItem(_binding: VhViewOrderItemBinding):RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrderItem {
        return VHViewOrderItem(VhViewOrderItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_view_order_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHViewOrderItem, position: Int) {
        holder.binding.itemName.text = cart[position].title
        holder.binding.addOnRecycler.adapter = AddonViewAdapter(context,cart[position].adns)
        holder.binding.itemVariant.text = cart[position].vari
        holder.binding.foodPrice.text = cart[position].fPrc.toString()
        holder.binding.itemQuantity.text = cart[position].fQnty.toString()
        holder.binding.totalUnitPrice.text = cart[position].tUPrc.toString()
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}