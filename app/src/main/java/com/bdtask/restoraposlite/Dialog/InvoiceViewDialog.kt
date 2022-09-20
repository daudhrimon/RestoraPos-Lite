package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.bdtask.restoraposlite.Adapter.InvoiceFoodAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.Model.Pay
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogInvoiceViewBinding
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import java.math.RoundingMode
import java.text.DecimalFormat

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
    private var resInf: Cstmr? = null
    private var posLogo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        _order = sharedPref.readOrder()!!
        super.onCreate(savedInstanceState)
        _binding = DialogInvoiceViewBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_invoice_view,null))
        setContentView(binding.root)

        posLogo = sharedPref.readPosLogo() ?: ""
        resInf = sharedPref.readResInf()

        Glide.with(context).asBitmap().load(posLogo).into(binding.logo)

        binding.address.text = "${resInf?.nm}\n${resInf?.adrs}\n${resInf?.eml}\n${resInf?.mbl}"

        if (state == 0){
            if (order.cart.isNotEmpty()){
                for (i in order.cart.indices) {
                    order.tPrc += order.cart[i].tUPrc
                }
            }
        }

        getCustomerPay(order.pay)

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        ///////////// get address from sharedPref

        binding.foodRecycler.adapter = InvoiceFoodAdapter(context,order.cart)

        binding.date.text = Util.getDate().toString()

        binding.customerName.text = order.odrInf.csInf.csNm

        binding.oprName.text = order.opr

        binding.subTotal.text = "${roundOfDecimal(order.tPrc)} $appCurrency"

        vat = (order.tPrc * order.vat)/100
        binding.vatTxTv.text = "Vat/Tax (${order.vat}%)"
        binding.vatTv.text = "$vat $appCurrency"

        crg = (order.tPrc * order.crg)/100
        binding.sCrgTv.text = "Service Crg (${order.crg}%)"
        binding.chargeTv.text = "$crg $appCurrency"

        binding.discountTv.text = "${order.dis} $appCurrency"

        grandTotal = (order.tPrc + vat + crg)-order.dis
        binding.grandtotalTV.text = "${roundOfDecimal(grandTotal)} $appCurrency"

        binding.totalDue.text = "${getTotalDue()} $appCurrency"

        binding.changeDue.text = "${getChangeDue()} $appCurrency"

        binding.customerPaid.text = "${roundOfDecimal(customerPay)} $appCurrency"

        binding.tableNo.text = order.odrInf.tbl

        binding.orderId.text = order.id.toString()



        binding.printBtn.setOnClickListener {
            if (posLogo != null && posLogo!!.isNotEmpty()) {
                InvoicePrintDialog(context,order,posLogo!!,resInf).show()
                dismiss()
            } else {
                Toasty.warning(context,"You Can't Print invoice Without a POS Logo",Toasty.LENGTH_SHORT,true).show()
            }
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

    private fun roundOfDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}