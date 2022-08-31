package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitterDetailsBinding

class SplitterDetailsAdapter(private val context: Context,
                             private val cartList: MutableList<Cart>
): RecyclerView.Adapter<SplitterDetailsAdapter.VHSplitterDetails>() {

    inner class VHSplitterDetails(binding: VhSplitterDetailsBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitterDetails {
        return VHSplitterDetails(VhSplitterDetailsBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter_details,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitterDetails, position: Int) {
        holder.binding.spDetName.text = cartList[position].title
        holder.binding.spDetVariant.text = cartList[position].vari
        holder.binding.spDetPrice.text = cartList[position].varPrc.toString()
        holder.binding.spDetQuantity.text = cartList[position].fQnty.toString()
        holder.binding.spDetTotalPrice.text = cartList[position].tUPrc.toString()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}