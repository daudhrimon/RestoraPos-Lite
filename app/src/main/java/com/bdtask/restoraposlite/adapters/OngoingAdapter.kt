package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.fragments.OngoingFragment.Companion.clickedList
import com.bdtask.restoraposlite.fragments.OngoingFragment.Companion.multiSelect
import com.bdtask.restoraposlite.interfaces.OngoingClickListener
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.databinding.VhOngoingBinding

class OngoingAdapter(
    private val context: Context,
    private var ongList: MutableList<Order>,
    private val ongoingClickListener: OngoingClickListener
) : RecyclerView.Adapter<OngoingAdapter.VHOngoing>() {

    var index = -1

    inner class VHOngoing(val binding: VhOngoingBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOngoing {
        return VHOngoing(
            VhOngoingBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_ongoing, parent, false)
            )
        )
    }


    override fun onBindViewHolder(holder: VHOngoing, position: Int) {
        try {
            holder.binding.cusName.text = "Customer : ${ongList[position].orderInfo.customerInfo.csNm}"
        } catch (e: Exception) {/**/
        }

        try {
            holder.binding.orderId.text = "Order Id : ${ongList[position].id}"
        } catch (e: Exception) {/**/
        }

        try {
            if (ongList[position].orderInfo.wtr.isNotEmpty()) {
                holder.binding.waiterName.visibility = View.VISIBLE
                holder.binding.waiterName.text = "Waiter : ${ongList[position].orderInfo.wtr}"
            } else {
                holder.binding.waiterName.visibility = View.INVISIBLE
            }
        } catch (e: Exception) {/**/
        }

        try {
            if (ongList[position].orderInfo.tbl.isNotEmpty()) {
                holder.binding.tableName.text = "Table : ${ongList[position].orderInfo.tbl}"
            } else {
                if (ongList[position].orderInfo.csTyp.isNotEmpty()) {
                    holder.binding.tableName.text = ongList[position].orderInfo.csTyp
                }
            }
        } catch (e: Exception) {/**/
        }

        ///////////////////////////////////////
        // setting Selected Background Color //
        ///////////////////////////////////////

        if (multiSelect) {
            if (clickedList.contains(position)) {
                holder.binding.table.setImageResource(R.drawable.selected_ongoing)
                holder.binding.layout.setBackgroundResource(R.drawable.shape_splitter_selected)
            } else {
                holder.binding.table.setImageResource(R.drawable.unselected_ongoing)
                holder.binding.layout.setBackgroundResource(R.drawable.ongoing_card_background)
            }
        } else {
            if (index == position) {
                holder.binding.table.setImageResource(R.drawable.selected_ongoing)
                holder.binding.layout.setBackgroundResource(R.drawable.shape_splitter_selected)
            } else {
                holder.binding.table.setImageResource(R.drawable.unselected_ongoing)
                holder.binding.layout.setBackgroundResource(R.drawable.ongoing_card_background)
            }
        }

        holder.itemView.setOnClickListener {
            if (multiSelect) {
                if (clickedList.isNotEmpty() && clickedList.contains(position)) {
                    clickedList.remove(position)
                } else {
                    clickedList.add(position)
                }
                ongoingClickListener.onGoingItemClick(position, clickedList.size)
            } else {
                // just single click
                var count = 0
                if (index == position) {
                    index = -1
                    count = 0
                } else {
                    index = position
                    count = 1
                }
                clickedList.clear()
                ongoingClickListener.onGoingItemClick(position, count)
            }
        }

        holder.itemView.setOnLongClickListener {
            if (clickedList.contains(position)) {
                clickedList.remove(position)
            } else {
                clickedList.add(position)
            }
            if (!multiSelect) {
                multiSelect = true
                index = -1
            }
            ongoingClickListener.onGoingItemClick(position, clickedList.size)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return ongList.size
    }
}