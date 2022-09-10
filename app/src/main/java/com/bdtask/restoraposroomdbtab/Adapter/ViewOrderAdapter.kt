package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Dialog.InvoiceViewDialog
import com.bdtask.restoraposroomdbtab.Dialog.ViewOrderDialog
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.VhViewOrderBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewOrderAdapter(private val context: Context,
                       private val todayList: MutableList<Order>): RecyclerView.Adapter<ViewOrderAdapter.VHViewOrder>() {
    var index = -1

    inner class VHViewOrder(_binding: VhViewOrderBinding): RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrder {
        return VHViewOrder(VhViewOrderBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_view_order,parent,false)))
    }

    override fun onBindViewHolder(holder: VHViewOrder, position: Int) {

        holder.binding.orderId.text = todayList[position].id.toString()
        holder.binding.cusName.text = todayList[position].odrInf.csInf.csNm
        holder.binding.cusType.text = todayList[position].odrInf.csTyp

        if(todayList[position].sts == 1){
            holder.binding.payStatus.text = "Paid"
        } else if (todayList[position].sts == 2){
            holder.binding.payStatus.text = "Cancel"
        }

        if (index == position){
            if (todayList[position].sts == 2){
                holder.binding.undoBtn.visibility = View.VISIBLE
            }
            holder.binding.expandLay.visibility = View.VISIBLE
            holder.binding.waiterName.text = todayList[position].odrInf.wtr
            holder.binding.tableNo.text = todayList[position].odrInf.tbl
            holder.binding.date.text = todayList[position].dat
            var payAmo = 0.0
            for (i in todayList[position].pay.indices){
                payAmo += todayList[position].pay[i].amo
            }
            holder.binding.amountTv.text = payAmo.toString()
        } else {
            holder.binding.expandLay.visibility = View.GONE
        }


        holder.binding.parentLay.setOnClickListener {
            if (index == position){
                holder.binding.expandLay.visibility = View.GONE
                index = -1
            } else {
                holder.binding.expandLay.visibility = View.VISIBLE
                index = position
            }
            notifyDataSetChanged()
        }


        holder.binding.viewBtn.setOnClickListener {
            val dialog = ViewOrderDialog(context,todayList[position])
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        holder.binding.printBtn.setOnClickListener {
            SharedPref.init(context)
            SharedPref.writeOrder(todayList[position])
            val dialog = InvoiceViewDialog(context,1)
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        holder.binding.undoBtn.setOnClickListener {

            todayList[position].sts = 0

            GlobalScope.launch(Dispatchers.IO) {

                MainActivity.database.orderDao().updateOrder(todayList[position])

                withContext(Dispatchers.Main){

                    Toasty.success(context,"Selected Order Moved To Ongoing",Toasty.LENGTH_SHORT,true).show()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return todayList.size
    }
}