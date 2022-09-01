package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhSplitItemBinding

class SplitFoodAdapter( private val context: Context,
                        private val cart: MutableList<Cart>,
                        private val splitFoodClickListener: SplitFoodClickListener ): RecyclerView.Adapter<SplitFoodAdapter.VHSplitFood>() {

    inner class VHSplitFood(binding: VhSplitItemBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitFood {
        return VHSplitFood(VhSplitItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_split_item,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitFood, position: Int) {

        if (cart[position].adns.isEmpty()) {

            holder.binding.spiTitle.text = cart[position].title

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
            holder.binding.spiTitle.text = cart[position].title + "\n( $title )"
        }

        holder.binding.spiQnty.text = cart[position].fQnty.toString()

        holder.itemView.setOnClickListener {

            splitFoodClickListener.onSplitFoodClick(position)

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return cart.size
    }
}