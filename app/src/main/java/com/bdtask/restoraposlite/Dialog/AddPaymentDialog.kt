package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogAddPaymentBinding
import com.google.gson.Gson
import es.dmoral.toasty.Toasty

class AddPaymentDialog(context: Context): Dialog(context) {
    private lateinit var binding: DialogAddPaymentBinding
    private val payType = arrayOf("Simple Payment","Card Payment")
    private var pos = 0
    private val sharedPref = SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogAddPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_add_payment,null))
        setContentView(binding.root)

        sharedPref.init(context)

        binding.typeSpinner.adapter = ArrayAdapter(context,androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,payType)

        binding.payCross.setOnClickListener {
            dismiss()
        }

        binding.root.setOnClickListener {
            hideKeyboard(binding)
            binding.terminal.clearFocus()
            binding.name.clearFocus()
        }


        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                pos = sPos
                hideKeyboard(binding)
                when (sPos){
                    0 -> {
                        binding.terminal.visibility = View.GONE
                        binding.name.hint = " Enter Payment Name"
                    }
                    1 -> {
                        binding.terminal.visibility = View.VISIBLE
                        binding.name.hint = " Enter Bank Name"
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        binding.addBtn.setOnClickListener {
            hideKeyboard(binding)
            if (pos == 0 && binding.name.text.toString().isEmpty()) {
                showToasty(0,"Empty Value Forbidden")
                return@setOnClickListener
            }

            if (pos == 1 && binding.terminal.text.toString().isEmpty() && binding.name.text.toString().isEmpty()) {
                showToasty(0,"Empty Value Forbidden")
                return@setOnClickListener
            }

            when (pos){
                0 -> {
                    var payList = mutableListOf<String>()
                    if (sharedPref.readPayList() != null) {
                        payList = sharedPref.readPayList()!!
                        if (!Gson().toJson(payList).contains(Gson().toJson(binding.name.text.toString()))){
                            payList.add(binding.name.text.toString())
                            sharedPref.writePayList(payList)
                            showToasty(1,"Added New Payment Successfully")
                        } else {
                            showToasty(0,"This Payment is Already In")
                        }
                    } else {
                        payList.add(binding.name.text.toString())
                        sharedPref.writePayList(payList)
                        showToasty(1,"Added New Payment Successfully")
                    }

                    binding.name.setText("")
                    binding.name.hint = " Enter Payment Name"

                    Log.wtf("Terminal",sharedPref.readTerminalList().toString())
                    Log.wtf("Bank",sharedPref.readBankList().toString())
                    Log.wtf("Pay",sharedPref.readPayList().toString())
                }

                1 -> {
                    if (binding.terminal.text.toString().isNotEmpty()) {
                        var terList = mutableListOf<String>()
                        if (sharedPref.readTerminalList() != null){
                            terList.clear()
                            terList = sharedPref.readTerminalList()!!
                            if (!Gson().toJson(terList).contains(Gson().toJson(binding.terminal.text.toString()))){
                                terList.add(binding.terminal.text.toString())
                                sharedPref.writeTerminalList(terList)
                                showToasty(1,"Added New Terminal Successfully")
                            } else {
                                showToasty(0,"This Terminal is Already In")
                            }
                        } else {
                            terList.add(binding.terminal.text.toString())
                            sharedPref.writeTerminalList(terList)
                            showToasty(1,"Added New Terminal Successfully")
                        }
                    }

                    if (binding.name.text.toString().isNotEmpty()){
                        if (sharedPref.readBankList() != null){
                            var bankList = mutableListOf<String>()
                            bankList = sharedPref.readBankList()!!
                            if (!Gson().toJson(bankList).contains(Gson().toJson(binding.name.text.toString()))){
                                bankList.add(binding.name.text.toString())
                                sharedPref.writeBankList(bankList)
                                showToasty(1,"Added New Bank Successfully")
                            } else {
                                showToasty(0,"This Bank is Already In")
                            }
                        } else {
                            val bankList = mutableListOf<String>()
                            bankList.add(binding.name.text.toString())
                            sharedPref.writeBankList(bankList)
                            showToasty(1,"Added New Bank Successfully")
                        }
                    }

                    binding.terminal.setText("")
                    binding.name.setText("")
                    binding.terminal.hint = " Enter Card Terminal"
                    binding.name.hint = " Enter Bank Name"

                    Log.wtf("Terminal",sharedPref.readTerminalList().toString())
                    Log.wtf("Bank",sharedPref.readBankList().toString())
                    Log.wtf("Pay",sharedPref.readPayList().toString())
                }
            }
        }
    }

    private fun hideKeyboard(binding: DialogAddPaymentBinding) {
        Util.hideSoftKeyBoard(context,binding.root)
    }

    private fun showToasty (status: Int, toast: String) {
        if (status == 1){
            Toasty.success(context,toast, Toasty.LENGTH_SHORT).show()
        } else {
            Toasty.info(context,toast, Toasty.LENGTH_SHORT).show()
        }
    }
}