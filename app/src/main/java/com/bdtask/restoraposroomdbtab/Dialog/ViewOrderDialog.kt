package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bdtask.restoraposroomdbtab.Adapter.SplitterDetailsAdapter
import com.bdtask.restoraposroomdbtab.Adapter.ViewOrderItemAdapter
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.DialogViewOrderBinding

class ViewOrderDialog(context: Context, private val order: Order): Dialog(context) {
    private var _binding: DialogViewOrderBinding? = null
    private val binding get() = _binding!!
    private var vat = 0.0
    private var crg = 0.0
    private var grandTotal = 0.0
    private var customerPay = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogViewOrderBinding.bind(layoutInflater.inflate(R.layout.dialog_view_order,null))
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        binding.itemRecycler.adapter = ViewOrderItemAdapter(context,order.cart)

        getCustomerPay(order.pay)

        binding.orderId.text = order.id.toString()
        if (order.tkn.isNotEmpty()){
            binding.tokenLay.visibility = View.VISIBLE
            binding.token.text = order.tkn
        }
        if (order.sts == 1){
            binding.status.text = "Paid"
        }
        binding.date.text = order.dat

        binding.cusName.text = order.odrInf.csInf.csNm
        binding.address.text = order.odrInf.csInf.csAdrs
        binding.mobile.text = order.odrInf.csInf.mbl

        vat = (order.tPrc * order.vat) / 100
        binding.vat.text = vat.toString()

        crg = (order.tPrc * order.crg) / 100
        binding.serviceCharge.text = crg.toString()

        binding.discount.text = order.dis.toString()

        grandTotal = (order.tPrc + vat + crg)-order.dis
        binding.grandTotal.text = grandTotal.toString()

        binding.totalDue.text = getTotalDue()

        binding.changeDue.text = getChangeDue()

        binding.customerPay.text = customerPay.toString()
    }

    private fun getCustomerPay(pay: MutableList<Pay>) {
        if(order.pay.isNotEmpty()){
            for (i in order.pay.indices){
                customerPay += order.pay[i].amo
            }
        }
    }

    private fun getTotalDue(): String {
        var ttlDue = 0.0
        return if (grandTotal > customerPay){
            ttlDue = grandTotal-customerPay
            if (ttlDue < 0){
                ttlDue = 0.0
                return ttlDue.toString()
            } else {
                ttlDue.toString()
            }
        } else {
            ttlDue.toString()
        }
    }

    private fun getChangeDue(): String {
        var cngDue = 0.0
        return if (customerPay > grandTotal){
            cngDue = customerPay - grandTotal
            cngDue.toString()
        } else {
            cngDue.toString()
        }
    }
}