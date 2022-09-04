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
                       private val order: Order ): Dialog(context), PayAmountTextChangedListener {

    private lateinit var binding: DialogPaymentBinding
    private val disTypes = arrayOf("Amount", "Percentage (%)")
    private var payList = mutableListOf<Pay>()
    private var disType by Delegates.notNull<Int>()
    private val sharedPref = SharedPref
    private var payments = mutableListOf<String>()
    private var terminals = mutableListOf<String>()
    private var banks = mutableListOf<String>()
    private var totalAmount = 0.0
    private var totalDue = 0.0
    private var payableAmount = 0.0
    private var returnable = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_payment,null))
        setContentView(binding.root)
        sharedPref.init(context)

        setPaymentHeaders()

        binding.spinDisType.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,disTypes)

        binding.spinDisType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                disType = sPos
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        binding.addDisBtn.setOnClickListener {
            binding.addDisBtn.visibility = View.GONE
            binding.distLay.visibility = View.VISIBLE
        }

        binding.discountEt.addTextChangedListener(object : TextWatcher{
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

        binding.paymentBtn.setOnClickListener {
            binding.addDisBtn.visibility = View.GONE
            binding.distLay.visibility = View.GONE
            binding.paymentBtn.visibility = View.GONE
            binding.payPrintBtn.visibility = View.VISIBLE
            payList.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,binding.root)
        }

        binding.addAnotherPay.setOnClickListener {
            payList.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,binding.root)
        }

        binding.payPrintBtn.setOnClickListener {

        }

    }

    private fun setPaymentHeaders() {
        totalAmount = 0.0
        totalDue = 0.0
        payableAmount = 0.0
        returnable = 0.0

        order.crg = 0.0
        for (i in order.cart.indices) {
            totalAmount += order.cart[i].tUPrc
        }
        order.vat = 0.0
        totalAmount += order.crg + order.vat
        totalDue = totalAmount - order.dis

        binding.totalAmount.text = totalAmount.toString()
        binding.totalDue.text = totalDue.toString()
        binding.payableAmount.text = totalDue.toString()
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

        binding.paymentRV.adapter = PaymentAdapter(context,
            payList, payments, terminals, banks,this)
    }

    override fun onPayAmountTextChange() {
        payableAmount = 0.0
        returnable = 0.0

        var adAmount = 0.0
        for (i in payList.indices) {
            adAmount += payList[i].amo
        }

        if (adAmount > totalDue) {
            payableAmount = 0.0
            returnable = adAmount - totalDue
        } else {
            payableAmount = totalDue - adAmount
        }
        binding.payableAmount.text = payableAmount.toString()
        binding.returnable.text = returnable.toString()

        if (payableAmount > 0) {
            binding.addAnotherPay.visibility = View.VISIBLE
        } else {
            binding.addAnotherPay.visibility = View.GONE
            Util.hideSoftKeyBoard(context,binding.root)
        }
    }

}