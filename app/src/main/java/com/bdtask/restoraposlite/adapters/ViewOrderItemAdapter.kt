package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhViewOrderItemBinding

class ViewOrderItemAdapter(
    private val context: Context,
    private val cart: MutableList<Cart>
) : RecyclerView.Adapter<ViewOrderItemAdapter.VHViewOrderItem>() {

    inner class VHViewOrderItem(val binding: VhViewOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrderItem {
        return VHViewOrderItem(
            VhViewOrderItemBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_view_order_item, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHViewOrderItem, position: Int) {
        if (cart[position].addon.isNotEmpty()) {
            holder.binding.addOnRecycler.adapter =
                ViewOrderAddonAdapter(context, cart[position].addon)
        }
        holder.binding.itemName.text = cart[position].title
        holder.binding.itemVariant.text = cart[position].variant
        holder.binding.foodPrice.text = cart[position].price.toString()
        holder.binding.itemQuantity.text = cart[position].quantity.toString()
        holder.binding.totalUnitPrice.text = cart[position].subTotal.toString()
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}