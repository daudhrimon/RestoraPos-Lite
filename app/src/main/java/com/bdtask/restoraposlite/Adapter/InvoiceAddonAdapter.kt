package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.Model.Adns
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhInvoiceAddonBinding

class InvoiceAddonAdapter(private val context: Context,
                          private val addons: MutableList<Adns>): RecyclerView.Adapter<InvoiceAddonAdapter.VHInvoiceAddon>() {

    inner class VHInvoiceAddon(binding: VhInvoiceAddonBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHInvoiceAddon {
        return VHInvoiceAddon(VhInvoiceAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_invoice_addon,parent,false)))
    }

    override fun onBindViewHolder(holder: VHInvoiceAddon, position: Int) {
        holder.binding.title.text = addons[position].adnNm
        holder.binding.quantity.text = "${addons[position].adnPrc / addons[position].adnQnty} * ${addons[position].adnQnty}"
        holder.binding.price.text = "${addons[position].adnPrc} $appCurrency"
    }

    override fun getItemCount(): Int {
        return addons.size
    }
}