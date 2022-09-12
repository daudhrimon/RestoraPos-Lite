package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.Adapter.PaymentAdapter
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Room.Entity.Split
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.DialogPaymentBinding
import kotlin.properties.Delegates

class SplitPaymentDialog(context: Context,
                         private val mode: Int,
                         private val order: Order,
                         private val split: Split ): Dialog(context) {

    private lateinit var binding: DialogPaymentBinding
    private val disTypes = arrayOf("Amount", "Percentage (%)")
    private var paymentList = mutableListOf<Pay>()
    private var disType by Delegates.notNull<Int>()
    private var discount = 0.0
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
            dismiss()
        }

        binding.addDisBtn.setOnClickListener {
            binding.addDisBtn.visibility = View.GONE
            binding.distLay.visibility = View.VISIBLE
        }

        binding.paymentBtn.setOnClickListener {
            if (binding.discountEt.text.toString().isNotEmpty()){
                discount = binding.discountEt.text.toString().toDouble()
            }
            binding.addDisBtn.visibility = View.GONE
            binding.distLay.visibility = View.GONE
            binding.paymentBtn.visibility = View.GONE
            binding.payPrintBtn.visibility = View.VISIBLE
            paymentList.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
        }

        binding.payPrintBtn.setOnClickListener {

        }

    }

    private fun setPaymentHeaders() {
        when(mode){
            0 -> {
                order.crg = 0.0
                // Complete Order
                for (i in order.cart.indices){
                    totalAmount += order.cart[i].tUPrc
                }
                order.vat = 0.0
                totalAmount += totalAmount + order.crg + order.vat
                binding.totalAmount.text = totalAmount.toString()
            }
            1 -> {
                // Split Order
            }
        }
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

        //binding.paymentRV.adapter = PaymentAdapter(context, paymentList, payments, terminals, banks)
    }
}