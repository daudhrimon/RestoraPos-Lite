package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Dialog.InvoiceViewDialog
import com.bdtask.restoraposlite.Dialog.ViewOrderDialog
import com.bdtask.restoraposlite.MainActivity
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.VhViewOrderBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewOrderAdapter(private val context: Context,
                       private val orderList: MutableList<Order>
                       ): RecyclerView.Adapter<ViewOrderAdapter.VHViewOrder>() {
    var index = -1

    inner class VHViewOrder(val binding: VhViewOrderBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrder {
        return VHViewOrder(VhViewOrderBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_view_order,parent,false)))
    }

    override fun onBindViewHolder(holder: VHViewOrder, position: Int) {

        holder.binding.orderId.text = orderList[position].id.toString()
        holder.binding.cusName.text = orderList[position].odrInf.csInf.csNm
        holder.binding.cusType.text = orderList[position].odrInf.csTyp

        if(orderList[position].sts == 1){
            holder.binding.payStatus.text = "Paid"
        } else if (orderList[position].sts == 2){
            holder.binding.payStatus.text = "Cancel"
        }

        if (index == position){
            if (orderList[position].sts == 2){
                holder.binding.undoBtn.visibility = View.VISIBLE
            }
            holder.binding.expandLay.visibility = View.VISIBLE
            holder.binding.waiterName.text = orderList[position].odrInf.wtr
            holder.binding.tableNo.text = orderList[position].odrInf.tbl
            holder.binding.date.text = orderList[position].dat

            if (orderList[position].sts != 2) {

                /*val vat = (orderList[position].tPrc*orderList[position].vat)/100
                val crg = (orderList[position].tPrc*orderList[position].crg)/100
                val grandTotal = (orderList[position].tPrc+vat+crg)-orderList[position].dis
                holder.binding.amountTv.text = "$grandTotal $appCurrency"*/

                var payAmo = 0.0
                for (i in orderList[position].pay.indices){
                    payAmo += orderList[position].pay[i].amo
                }
                holder.binding.amountTv.text = "$payAmo $appCurrency"
            }
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
            val dialog = ViewOrderDialog(context,orderList[position])
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        holder.binding.printBtn.setOnClickListener {
            SharedPref.init(context)
            SharedPref.writeOrder(orderList[position])
            val dialog = InvoiceViewDialog(context,1)
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        holder.binding.undoBtn.setOnClickListener {

            orderList[position].sts = 0

            GlobalScope.launch(Dispatchers.IO) {

                MainActivity.database.AppDao().updateOrder(orderList[position])

                withContext(Dispatchers.Main){

                    Toasty.success(context,"Selected Order Moved To Ongoing",Toasty.LENGTH_SHORT,true).show()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}