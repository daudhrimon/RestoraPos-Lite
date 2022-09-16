package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhViewOrderItemBinding

class ViewOrderItemAdapter(private val context: Context,
                           private val cart:MutableList<Cart>
                           ): RecyclerView.Adapter<ViewOrderItemAdapter.VHViewOrderItem>() {

    inner class VHViewOrderItem(val binding: VhViewOrderItemBinding):RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrderItem {
        return VHViewOrderItem(VhViewOrderItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_view_order_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHViewOrderItem, position: Int) {
        if (cart[position].adns.isNotEmpty()){
            holder.binding.addOnRecycler.adapter = ViewOrderAddonAdapter(context,cart[position].adns)
        }
        holder.binding.itemName.text = cart[position].title
        holder.binding.itemVariant.text = cart[position].vari
        holder.binding.foodPrice.text = cart[position].fPrc.toString()
        holder.binding.itemQuantity.text = cart[position].fQnty.toString()
        holder.binding.totalUnitPrice.text = cart[position].tUPrc.toString()
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}