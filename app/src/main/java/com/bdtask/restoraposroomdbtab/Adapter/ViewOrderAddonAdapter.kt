package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Adns
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhViewOrderAddonBinding

class ViewOrderAddonAdapter(private val context: Context,
                            private val addons: MutableList<Adns>): RecyclerView.Adapter<ViewOrderAddonAdapter.VHViewOrderAddon>() {

    class VHViewOrderAddon(_binding: VhViewOrderAddonBinding): RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrderAddon {
        return VHViewOrderAddon(VhViewOrderAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_view_order_addon,parent,false)))
    }

    override fun onBindViewHolder(holder: VHViewOrderAddon, position: Int) {
        holder.binding.addons.text = "${addons[position].adnNm} * ${addons[position].adnQnty}"

    }

    override fun getItemCount(): Int {
        return addons.size
    }
}