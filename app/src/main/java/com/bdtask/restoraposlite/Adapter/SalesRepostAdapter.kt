package com.bdtask.restoraposlite.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Dialog.InvoiceViewDialog
import com.bdtask.restoraposlite.Dialog.ViewOrderDialog
import com.bdtask.restoraposlite.Fragment.ReportFragment.Companion.salesTotal
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.VhSalesReportBinding
import com.bdtask.restoraposlite.databinding.VhViewOrderBinding

class SalesRepostAdapter(
    private val context: Context,
    private val orderList: MutableList<Order>): RecyclerView.Adapter<SalesRepostAdapter.VHSalesRepo>() {

    private var index = -1

    inner class VHSalesRepo(val binding: VhSalesReportBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSalesRepo {
        return VHSalesRepo(VhSalesReportBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_sales_report,parent,false)))
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: VHSalesRepo, position: Int) {

        /*val vat = (orderList[position].tPrc*orderList[position].vat)/100
        val crg = (orderList[position].tPrc*orderList[position].crg)/100
        val grandTotal = (orderList[position].tPrc+vat+crg)-orderList[position].dis*/

        var payAmo = 0.0
        var payType = arrayListOf<String>()
        for (i in orderList[position].pay.indices){
            payAmo += orderList[position].pay[i].amo
            payType.add(orderList[position].pay[i].pay)
        }

        holder.binding.orderId.text = orderList[position].id.toString()
        holder.binding.cusName.text = orderList[position].odrInf.csInf.csNm
        holder.binding.cusType.text = orderList[position].odrInf.csTyp
        holder.binding.amountTv.text = "$payAmo $appCurrency"

        if (index == position){
            holder.binding.expandLay.visibility = View.VISIBLE
            holder.binding.waiterName.text = orderList[position].odrInf.wtr
            holder.binding.tableNo.text = orderList[position].odrInf.tbl
            holder.binding.date.text = orderList[position].dat
            val pay = payType.toString().replace("[","").replace("]","")
            holder.binding.payType.text = pay
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
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}