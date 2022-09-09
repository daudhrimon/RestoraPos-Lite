package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.appCurrency
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
        if (cart[position].adns.isNotEmpty()){
            holder.binding.addOnRecycler.adapter = ViewOrderAddonAdapter(context,cart[position].adns)
        }
        holder.binding.itemName.text = cart[position].title
        holder.binding.itemVariant.text = cart[position].vari
        holder.binding.foodPrice.text = "${cart[position].fPrc} $appCurrency"
        holder.binding.itemQuantity.text = cart[position].fQnty.toString()
        holder.binding.totalUnitPrice.text = "${cart[position].tUPrc} $appCurrency"
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}