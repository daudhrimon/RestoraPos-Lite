package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.bdtask.restoraposroomdbtab.Adapter.InvoiceFoodAdapter
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogInvoiceViewBinding

class InvoiceViewDialog(context: Context, private val state: Int): Dialog(context) {

    private var _binding: DialogInvoiceViewBinding? = null
    private val binding get() = _binding!!
    private val sharedPref = SharedPref
    private var _order:Order? = null
    private val order get() = _order!!
    private var vat = 0.0
    private var crg = 0.0
    private var grandTotal = 0.0
    private var customerPay = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        _order = sharedPref.readSharedOrder()!!
        super.onCreate(savedInstanceState)
        _binding = DialogInvoiceViewBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_invoice_view,null))
        setContentView(binding.root)
        Log.wtf("InvoiceViewDialogOrders",order.toString())

        if (state == 0){
            if (order.cart.isNotEmpty()){
                for (i in order.cart.indices) {
                    order.tPrc += order.cart[i].tUPrc
                }
            }
        }

        getCustomerPay(order.pay)

        ///////////// get address from sharedPref

        binding.foodRecycler.adapter = InvoiceFoodAdapter(context,order.cart)

        binding.logo.setImageResource(R.drawable.poslogo)

        binding.address.text = context.getString(R.string.address)

        binding.date.text = Util.getDate().toString()

        binding.customerName.text = order.odrInf.csInf.csNm

        binding.counterName.text = "Daud"

        binding.subTotal.text = order.tPrc.toString()

        vat = (order.tPrc * order.vat) / 100
        binding.vatTv.text = vat.toString()

        crg = (order.tPrc * order.crg) / 100
        binding.chargeTv.text = crg.toString()

        binding.discountTv.text = order.dis.toString()

        grandTotal = order.tPrc + vat + crg
        binding.grandtotalTV.text = grandTotal.toString()

        binding.totalDue.text = getTotalDue()

        binding.changeDue.text = getChangeDue()

        binding.customerPaid.text = customerPay.toString()

        binding.tableNo.text = order.odrInf.tbl

        binding.orderId.text = order.id.toString()

        binding.printBtn.setOnClickListener {
            onBackPressed()
            InvoicePrintDialog(context,order).show()
        }
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
            ttlDue = (grandTotal-order.dis)-customerPay
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