package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitterDetailsBinding

class SplitterDetailsAdapter(private val context: Context,
                             private val cartList: List<Cart>
): RecyclerView.Adapter<SplitterDetailsAdapter.VHSplitterDetails>() {

    inner class VHSplitterDetails(binding: VhSplitterDetailsBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitterDetails {
        return VHSplitterDetails(VhSplitterDetailsBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter_details,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitterDetails, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}