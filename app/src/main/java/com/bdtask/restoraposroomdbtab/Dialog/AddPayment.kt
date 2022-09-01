package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogAddPaymentBinding
import com.google.gson.Gson
import es.dmoral.toasty.Toasty

class AddPayment(context: Context): Dialog(context) {
    private lateinit var binding: DialogAddPaymentBinding
    private val paymentType = arrayOf("Simple Payment","Card Payment")
    private var pos = 0
    private val sharedPref = SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogAddPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_add_payment,null))
        setContentView(binding.root)

        sharedPref.init(context)

        binding.typeSpinner.adapter = ArrayAdapter(context,androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,paymentType)

        binding.payCross.setOnClickListener {
            onBackPressed()
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
                Toasty.warning(context, "Empty Value Forbidden", Toasty.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pos == 1 && binding.terminal.text.toString().isEmpty() && binding.name.text.toString().isEmpty()) {
                Toasty.warning(context, "Empty Value Forbidden", Toasty.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var payList = mutableListOf<String>()

            when (pos){
                0 -> {
                    if (sharedPref.readPayList() != null){
                        payList.clear()
                        payList = sharedPref.readPayList()!!
                        if (!Gson().toJson(payList).contains(Gson().toJson(binding.name.text.toString()))){
                            payList.add(binding.name.text.toString())
                            sharedPref.writePayList(payList)
                            Toasty.success(context,"Add New Payment Successfully", Toasty.LENGTH_SHORT).show()
                        } else {
                            Toasty.warning(context,"This Payment is Already In", Toasty.LENGTH_SHORT).show()
                        }
                    } else {
                        payList.clear()
                        payList.add(binding.name.text.toString())
                        sharedPref.writePayList(payList)
                        Toasty.success(context,"Add New Payment Successfully", Toasty.LENGTH_SHORT).show()
                    }



                    binding.name.setText("")
                    binding.name.hint = " Enter Payment Name"

                    Log.wtf("Terminal",sharedPref.readTerminalList().toString())
                    Log.wtf("Bank",sharedPref.readBankList().toString())
                    Log.wtf("Pay",sharedPref.readPayList().toString())
                }

                1 -> {
                    var terList = mutableListOf<String>()

                    if (sharedPref.readTerminalList() != null){
                        terList.clear()
                        terList = sharedPref.readTerminalList()!!
                        if (!Gson().toJson(terList).contains(Gson().toJson(binding.terminal.text.toString()))){
                            payList.add(binding.terminal.text.toString())
                            sharedPref.writeTerminalList(terList)
                            Toasty.success(context,"Add New Terminal Successfully", Toasty.LENGTH_SHORT).show()
                        } else {
                            Toasty.warning(context,"This Terminal is Already In", Toasty.LENGTH_SHORT).show()
                        }
                    } else {
                        terList.clear()
                        terList.add(binding.terminal.text.toString())
                        sharedPref.writeTerminalList(terList)
                        Toasty.success(context,"Add New Terminal Successfully", Toasty.LENGTH_SHORT).show()
                    }

                    if (sharedPref.readBankList() != null){
                        payList.clear()
                        payList = sharedPref.readBankList()!!
                        if (!Gson().toJson(payList).contains(Gson().toJson(binding.name.text.toString()))){
                            payList.add(binding.name.text.toString())
                            sharedPref.writeBankList(payList)
                            Toasty.success(context,"Add New Bank Successfully", Toasty.LENGTH_SHORT).show()
                        } else {
                            Toasty.warning(context,"This Bank is Already In", Toasty.LENGTH_SHORT).show()
                        }
                    } else {
                        payList.clear()
                        payList.add(binding.name.text.toString())
                        sharedPref.writeBankList(payList)
                        Toasty.success(context,"Add New Bank Successfully", Toasty.LENGTH_SHORT).show()
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
}