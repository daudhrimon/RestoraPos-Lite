package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.Adapter.PaymentAdapter
import com.bdtask.restoraposroomdbtab.Interface.PayAmountTextChangedListener
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogPaymentBinding
import kotlin.properties.Delegates

class CPaymentDialog ( context: Context,
                       private val order: Order ): Dialog(context)/*, PayAmountTextChangedListener*/ {

    private lateinit var dBinding: DialogPaymentBinding
    private val disTypes = arrayOf("Amount", "Percentage (%)")
    private var disType by Delegates.notNull<Int>()
    private val sharedPref = SharedPref
    private var payments = mutableListOf<String>()
    private var terminals = mutableListOf<String>()
    private var banks = mutableListOf<String>()
    private var totalAmount = 0.0
    private var totalDue = 0.0
    private var payableAmount = 0.0
    private var changeDue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dBinding = DialogPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_payment,null))
        setContentView(dBinding.root)
        sharedPref.init(context)

        setPaymentHeaders()

        dBinding.spinDisType.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,disTypes)

        dBinding.spinDisType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                disType = sPos
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        dBinding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        dBinding.addDisBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.VISIBLE
        }

        dBinding.discountEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/**/}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()){
                    order.dis = p0.toString().toDouble()
                } else {
                    order.dis = 0.0
                }
                setPaymentHeaders()
            }
            override fun afterTextChanged(p0: Editable?) {/**/}
        })

        dBinding.paymentBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.GONE
            dBinding.paymentBtn.visibility = View.GONE
            dBinding.payPrintBtn.visibility = View.VISIBLE
            order.pay.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,dBinding.root)
        }

        dBinding.addAnotherPay.setOnClickListener {
            order.pay.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,dBinding.root)
        }

        dBinding.payPrintBtn.setOnClickListener {

        }

    }

    private fun setPaymentHeaders() {
        totalAmount = 0.0
        totalDue = 0.0
        payableAmount = 0.0
        changeDue = 0.0

        order.crg = 0.0
        for (i in order.cart.indices) {
            totalAmount += order.cart[i].tUPrc
        }
        order.vat = 0.0
        order.tPrc = totalAmount

        totalAmount += order.crg + order.vat
        totalDue = totalAmount - order.dis

        dBinding.totalAmount.text = totalAmount.toString()
        dBinding.totalDue.text = totalDue.toString()
        dBinding.payableAmount.text = totalDue.toString()
    }

    private fun setPaymentAdapter() {
        if (sharedPref.readPayList() != null){
            payments = sharedPref.readPayList()!!
        }
        if (sharedPref.readTerminalList() != null){
            terminals = sharedPref.readTerminalList()!!
        }
        if (sharedPref.readBankList() != null){
            banks = sharedPref.readBankList()!!
        }

        dBinding.paymentRV.adapter = PaymentAdapter(context, order.pay, payments, terminals, banks,
            dBinding.totalDue,dBinding.payableAmount,dBinding.changeDue,dBinding.addAnotherPay)
    }

    /*override fun onPayAmountTextChange() {
        payableAmount = 0.0
        changeDue = 0.0

        var adAmount = 0.0
        for (i in payList.indices) {
            adAmount += payList[i].amo
        }

        if (adAmount > totalDue) {
            payableAmount = 0.0
            changeDue = adAmount - totalDue
        } else {
            payableAmount = totalDue - adAmount
        }
        dBinding.payableAmount.text = payableAmount.toString()
        dBinding.changeDue.text = changeDue.toString()

        if (payableAmount > 0) {
            dBinding.addAnotherPay.visibility = View.VISIBLE
        } else {
            dBinding.addAnotherPay.visibility = View.GONE
            Util.hideSoftKeyBoard(context,dBinding.root)
        }
    }*/

}