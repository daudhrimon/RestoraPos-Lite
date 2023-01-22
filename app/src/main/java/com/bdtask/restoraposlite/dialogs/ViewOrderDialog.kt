package com.bdtask.restoraposlite.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bdtask.restoraposlite.adapters.ViewOrderItemAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.models.Payments
import com.bdtask.restoraposlite.room.entity.Customer
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.databinding.DialogViewOrderBinding
import com.bumptech.glide.Glide

class ViewOrderDialog(context: Context, private val order: Order) : Dialog(context) {
    private var _binding: DialogViewOrderBinding? = null
    private val binding get() = _binding!!
    private val sharedPref = SharedPref
    private var customerPay = 0.0
    private var resInf: Customer? = null
    private var posLogo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref.init(context)
        _binding = DialogViewOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        posLogo = sharedPref.readPosLogo() ?: ""
        resInf = sharedPref.readResInf()

        Glide.with(context).asBitmap().load(posLogo).into(binding.logo)


        binding.addressTv.text = "${resInf?.name}\n${resInf?.address}\n${resInf?.email}\n${resInf?.mobile}"


        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding.itemRecycler.adapter = ViewOrderItemAdapter(context, order.cart)

        getCustomerPay(order.payments)

        binding.orderId.text = order.id.toString()
        if (order.token.isNotEmpty()) {
            binding.tokenLay.visibility = View.VISIBLE
            binding.token.text = order.token
        }
        if (order.status == 1) {
            binding.status.text = "Paid"
        }
        binding.date.text = order.date

        binding.cusName.text = order.orderInfo.customerInfo.csNm
        binding.address.text = order.orderInfo.customerInfo.csAdrs
        binding.mobile.text = order.orderInfo.customerInfo.mbl

        binding.opName.text = order.operator

        binding.subTotaltv.text = "${order.subTotal} $appCurrency"


        binding.vatTxTv.text = "Vat/Tax (${order.vat}%)"
        binding.vat.text = "${order.vatTotal} $appCurrency"


        binding.sCrgTv.text = "Service Crg (${order.charge}%)"
        binding.serviceCharge.text = "${order.chargeTotal} $appCurrency"

        binding.discount.text = "${order.discount} $appCurrency"


        binding.grandTotal.text = "${order.grandTotal} $appCurrency"

        binding.totalDue.text = "${getTotalDue()} $appCurrency"

        binding.changeDue.text = "${getChangeDue()} $appCurrency"

        binding.customerPay.text = "$customerPay $appCurrency"
    }

    private fun getCustomerPay(payments: MutableList<Payments>) {
        if (payments.isNotEmpty()) {
            for (i in payments.indices) {
                customerPay += payments[i].amo
            }
        }
    }

    private fun getTotalDue(): String {
        var ttlDue = 0.0
        return if (order.grandTotal > customerPay) {
            ttlDue = order.grandTotal - customerPay
            if (ttlDue < 0) {
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
        return if (customerPay > order.grandTotal) {
            cngDue = customerPay - order.grandTotal
            cngDue.toString()
        } else {
            cngDue.toString()
        }
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}
