package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.models.Addon
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhViewOrderAddonBinding

class ViewOrderAddonAdapter(
    private val context: Context,
    private val addons: MutableList<Addon>
) : RecyclerView.Adapter<ViewOrderAddonAdapter.VHViewOrderAddon>() {

    class VHViewOrderAddon(val binding: VhViewOrderAddonBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrderAddon {
        return VHViewOrderAddon(
            VhViewOrderAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_view_order_addon, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHViewOrderAddon, position: Int) {
        holder.binding.addons.text = "${addons[position].name} * ${addons[position].quantity}"
    }

    override fun getItemCount(): Int {
        return addons.size
    }
}