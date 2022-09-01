package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitterDetailsBinding

class SplitterDetailsAdapter( private val context: Context,
                              private val cart: MutableList<Cart> ): RecyclerView.Adapter<SplitterDetailsAdapter.VHSplitterDetails>() {

    inner class VHSplitterDetails(binding: VhSplitterDetailsBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitterDetails {
        return VHSplitterDetails(VhSplitterDetailsBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter_details,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitterDetails, position: Int) {

        if (cart[position].adns.isEmpty()) {

            holder.binding.spDetName.text = cart[position].title

        } else {

            var title = ""
            var title1 = ""
            var count = true
            for (i in cart[position].adns.indices) {
                if (count) {
                    title1 = cart[position].adns[i].adnNm
                    count = false
                }
                if (cart[position].adns[i].adnNm != title && cart[position].adns[i].adnNm.isNotEmpty()) {
                    title = title1 + ", " + cart[position].adns[i].adnNm
                }
            }
            holder.binding.spDetName.text = cart[position].title + "\n( $title )"
        }

        holder.binding.spDetVariant.text = cart[position].vari
        holder.binding.spDetPrice.text = cart[position].varPrc.toString()
        holder.binding.spDetQuantity.text = cart[position].fQnty.toString()
        holder.binding.spDetTotalPrice.text = cart[position].tUPrc.toString()
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}