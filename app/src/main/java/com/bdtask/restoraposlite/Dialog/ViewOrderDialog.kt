package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bdtask.restoraposlite.Adapter.ViewOrderItemAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.Model.Pay
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.DialogViewOrderBinding
import com.bumptech.glide.Glide

class ViewOrderDialog(context: Context, private val order: Order): Dialog(context) {

    private var _binding: DialogViewOrderBinding? = null
    private val binding get() = _binding!!
    private val sharedPref = SharedPref
    private var vat = 0.0
    private var crg = 0.0
    private var grandTotal = 0.0
    private var customerPay = 0.0
    private var resInf: Cstmr? = null
    private var posLogo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref.init(context)
        _binding = DialogViewOrderBinding.bind(layoutInflater.inflate(R.layout.dialog_view_order,null))
        setContentView(binding.root)

        posLogo = sharedPref.readPosLogo() ?: ""
        resInf = sharedPref.readResInf()

        Glide.with(context).asBitmap().placeholder(R.drawable.poslogo).load(posLogo).into(binding.logo)


        binding.addressTv.text = "${resInf?.nm ?: "Restora POS Lite"}\n${resInf?.adrs ?: "Mannan Plaza, Khilkhet, Dhaka"}\n${resInf?.eml ?: "Email: bdtask@gmail.com"}\n${resInf?.mbl  ?: "Mobile: 0123456789"}"


        binding.closeBtn.setOnClickListener {
            dismiss()
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

        binding.opName.text = order.opr

        vat = (order.tPrc * order.vat) / 100
        binding.vat.text = "$vat $appCurrency"

        crg = (order.tPrc * order.crg) / 100
        binding.serviceCharge.text = "$crg $appCurrency"

        binding.discount.text = "${order.dis} $appCurrency"

        grandTotal = (order.tPrc + vat + crg)-order.dis
        binding.grandTotal.text = "$grandTotal $appCurrency"

        binding.totalDue.text = "${getTotalDue()} $appCurrency"

        binding.changeDue.text = "${getChangeDue()} $appCurrency"

        binding.customerPay.text = "$customerPay $appCurrency"
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
