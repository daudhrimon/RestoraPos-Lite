package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.DialogPaymentBinding

class Payment(context: Context): Dialog(context) {
    private lateinit var binding: DialogPaymentBinding
    private val disTypes = arrayOf("Amount", "Percentage (%)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_payment,null))
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        binding.addDis.setOnClickListener {
            binding.addDis.visibility = View.GONE
            binding.distLay.visibility = View.VISIBLE
        }

        binding.payment.setOnClickListener {
            binding.addDis.visibility = View.GONE
            binding.distLay.visibility = View.GONE
        }
    }
}