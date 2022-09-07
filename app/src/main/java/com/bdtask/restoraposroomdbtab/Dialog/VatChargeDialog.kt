package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.DialogSingleItemetBinding
import es.dmoral.toasty.Toasty

class VatChargeDialog(context: Context,
                      private val status: Int,
                      private val header: String,
                      private val hint: String): Dialog(context) {

    private lateinit var binding: DialogSingleItemetBinding
    private val sharedPref = SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        super.onCreate(savedInstanceState)
        binding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        setContentView(binding.root)
        binding.itemTv.text = header
        binding.itemEt.hint = hint
        binding.itemEt.inputType = InputType.TYPE_CLASS_NUMBER

        binding.itemCross.setOnClickListener {
            onBackPressed()
        }

        binding.itemBtn.setOnClickListener {
            if (binding.itemEt.text.toString().isEmpty()){
                binding.itemEt.error = "Empty Value Cant Save"
                binding.itemEt.requestFocus()
                return@setOnClickListener
            }

            if (status==0){
                sharedPref.writeVat(binding.itemEt.text.toString())
                Toasty.success(context,"${sharedPref.readVat()!!} % Added as Global Vat",Toasty.LENGTH_SHORT).show()
                onBackPressed()
            } else {
                sharedPref.writeCharge(binding.itemEt.text.toString())
                Toasty.success(context,"${sharedPref.readCharge()!!} % Added as Global Service Charge",Toasty.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
}