package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.dialogs.InvoiceViewDialog
import com.bdtask.restoraposlite.dialogs.ViewOrderDialog
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.databinding.VhViewOrderBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat

class ViewOrderAdapter(
    private val context: Context,
    private val orderList: MutableList<Order>,
    private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<ViewOrderAdapter.VHViewOrder>() {
    var index = -1

    inner class VHViewOrder(val binding: VhViewOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHViewOrder {
        return VHViewOrder(
            VhViewOrderBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_view_order, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHViewOrder, position: Int) {

        holder.binding.orderId.text = orderList[position].id.toString()
        holder.binding.cusName.text = orderList[position].orderInfo.customerInfo.csNm
        holder.binding.cusType.text = orderList[position].orderInfo.csTyp

        if (orderList[position].status == 1) {
            holder.binding.payStatus.text = "Paid"
        } else if (orderList[position].status == 2) {
            holder.binding.payStatus.text = "Cancel"
        }

        if (index == position) {
            if (orderList[position].status == 2) {
                holder.binding.undoBtn.visibility = View.VISIBLE
            }
            holder.binding.expandLay.visibility = View.VISIBLE
            holder.binding.waiterName.text = orderList[position].orderInfo.wtr
            holder.binding.tableNo.text = orderList[position].orderInfo.tbl
            holder.binding.date.text = orderList[position].date

            if (orderList[position].status != 2) {

                /*val vat = (orderList[position].tPrc*orderList[position].vat)/100
                val crg = (orderList[position].tPrc*orderList[position].crg)/100
                val grandTotal = (orderList[position].tPrc+vat+crg)-orderList[position].dis
                holder.binding.amountTv.text = "$grandTotal $appCurrency"*/

                var payAmo = 0.0
                for (i in orderList[position].payments.indices) {
                    payAmo += orderList[position].payments[i].amo
                }
                holder.binding.amountTv.text = "${roundOfDecimal(payAmo)} $appCurrency"
            }
        } else {
            holder.binding.expandLay.visibility = View.GONE
        }


        holder.binding.parentLay.setOnClickListener {
            if (index == position) {
                holder.binding.expandLay.visibility = View.GONE
                index = -1
            } else {
                holder.binding.expandLay.visibility = View.VISIBLE
                index = position
            }
            notifyDataSetChanged()
        }


        holder.binding.viewBtn.setOnClickListener {
            val dialog = ViewOrderDialog(context, orderList[position])
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width) / 15, (24 * height) / 25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        holder.binding.printBtn.setOnClickListener {
            SharedPref.init(context)
            val dialog = InvoiceViewDialog(context,orderList[position])
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width) / 15, (24 * height) / 25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        holder.binding.undoBtn.setOnClickListener {

            orderList[position].status = 0


            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(context.applicationContext).AppDao().updateOrder(orderList[position])

                withContext(Dispatchers.Main) {

                    Toasty.success(
                        context,
                        "Selected Order Moved To Ongoing",
                        Toasty.LENGTH_SHORT,
                        true
                    ).show()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    private fun roundOfDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}