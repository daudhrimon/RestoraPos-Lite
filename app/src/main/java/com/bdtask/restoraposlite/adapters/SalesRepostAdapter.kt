package com.bdtask.restoraposlite.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.dialogs.InvoiceViewDialog
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.databinding.VhSalesReportBinding

class SalesRepostAdapter(
    private val context: Context,
    private val orderList: MutableList<Order>
) : RecyclerView.Adapter<SalesRepostAdapter.VHSalesRepo>() {

    //private var index = -1

    inner class VHSalesRepo(val binding: VhSalesReportBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSalesRepo {
        return VHSalesRepo(
            VhSalesReportBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_sales_report, parent, false)
            )
        )
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: VHSalesRepo, position: Int) {

        var payAmo = 0.0
        val payType = arrayListOf<String>()
        for (i in orderList[position].payments.indices) {
            payAmo += orderList[position].payments[i].amo
            payType.add(orderList[position].payments[i].pay)
        }

        holder.binding.orderId.text = orderList[position].id.toString()
        holder.binding.cusName.text = orderList[position].orderInfo.customerInfo.csNm
        holder.binding.cusType.text = orderList[position].orderInfo.csTyp
        holder.binding.amountTv.text = "$payAmo $appCurrency"

        holder.binding.waiterName.text = orderList[position].orderInfo.wtr
        holder.binding.tableNo.text = orderList[position].orderInfo.tbl
        holder.binding.date.text = orderList[position].date

        holder.binding.vatTv.text = "${orderList[position].vat} $appCurrency"
        holder.binding.crgTv.text = "${orderList[position].charge} $appCurrency"
        holder.binding.discountTv.text = "${orderList[position].discount} $appCurrency"
        holder.binding.subTotalTv.text = "${orderList[position].subTotal} $appCurrency"

        val pay = payType.toString().replace("[", "").replace("]", "")
        holder.binding.payType.text = pay

        holder.itemView.setOnClickListener {
            SharedPref.init(context)
            val dialog = InvoiceViewDialog(context,orderList[position])
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width) / 15, (24 * height) / 25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        /*if (index == position) {
            holder.binding.expandLay.visibility = View.VISIBLE
            holder.binding.waiterName.text = orderList[position].odrInf.wtr
            holder.binding.tableNo.text = orderList[position].odrInf.tbl
            holder.binding.date.text = orderList[position].dat
            val pay = payType.toString().replace("[", "").replace("]", "")
            holder.binding.payType.text = pay
        } else {
            holder.binding.expandLay.visibility = View.GONE
        }*/


        /*holder.binding.parentLay.setOnClickListener {
            if (index == position) {
                holder.binding.expandLay.visibility = View.GONE
                index = -1
            } else {
                holder.binding.expandLay.visibility = View.VISIBLE
                index = position
            }
            notifyDataSetChanged()
        }*/


        /*holder.binding.viewBtn.setOnClickListener {
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
            SharedPref.writeOrder(orderList[position])
            val dialog = InvoiceViewDialog(context, 1)
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width) / 15, (24 * height) / 25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }*/
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}