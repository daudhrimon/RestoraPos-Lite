package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.models.Addon
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhInvoiceAddonBinding

class InvoiceAddonAdapter(
    private val context: Context,
    private val addons: MutableList<Addon>
) : RecyclerView.Adapter<InvoiceAddonAdapter.VHInvoiceAddon>() {

    inner class VHInvoiceAddon(val binding: VhInvoiceAddonBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHInvoiceAddon {
        return VHInvoiceAddon(
            VhInvoiceAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_invoice_addon, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHInvoiceAddon, position: Int) {
        holder.binding.title.text = addons[position].name
        holder.binding.quantity.text =
            "${addons[position].price / addons[position].quantity} * ${addons[position].quantity}"
        holder.binding.price.text = "${addons[position].price} $appCurrency"
    }

    override fun getItemCount(): Int {
        return addons.size
    }
}