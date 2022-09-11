package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.appCurrency
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhInvoiceFoodBinding

class InvoiceFoodAdapter(private val context: Context,
                         private val cart: MutableList<Cart>): RecyclerView.Adapter<InvoiceFoodAdapter.VHInvoiceFood>() {

    inner class VHInvoiceFood(binding: VhInvoiceFoodBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHInvoiceFood {
        return VHInvoiceFood(VhInvoiceFoodBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_invoice_food,parent,false)))
    }

    override fun onBindViewHolder(holder: VHInvoiceFood, position: Int) {
        if (cart[position].adns.isNotEmpty()){
            holder.binding.addonsRecycler.adapter = InvoiceAddonAdapter(context,cart[position].adns)
        }
        holder.binding.title.text = cart[position].title
        holder.binding.variant.text = cart[position].vari
        holder.binding.quantity.text = "${cart[position].varPrc} * ${cart[position].fQnty}"
        holder.binding.price.text = "${cart[position].fPrc} $appCurrency"
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}