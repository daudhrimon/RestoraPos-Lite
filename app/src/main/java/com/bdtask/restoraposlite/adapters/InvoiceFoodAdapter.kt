package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhInvoiceFoodBinding

class InvoiceFoodAdapter(
    private val context: Context,
    private val cart: MutableList<Cart>
) : RecyclerView.Adapter<InvoiceFoodAdapter.VHInvoiceFood>() {

    inner class VHInvoiceFood(val binding: VhInvoiceFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHInvoiceFood {
        return VHInvoiceFood(
            VhInvoiceFoodBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_invoice_food, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHInvoiceFood, position: Int) {
        if (cart[position].addon.isNotEmpty()) {
            holder.binding.addonsRecycler.adapter =
                InvoiceAddonAdapter(context, cart[position].addon)
        }
        holder.binding.title.text = cart[position].title
        holder.binding.variant.text = cart[position].variant
        holder.binding.quantity.text = "${cart[position].varPrice} * ${cart[position].quantity}"
        holder.binding.price.text = "${cart[position].price} $appCurrency"
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}