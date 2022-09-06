package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.bdtask.restoraposroomdbtab.Adapter.InvoiceFoodAdapter
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogInvoiceViewBinding

class InvoiceViewDialog(context: Context,
                        private val order: Order): Dialog(context) {

    private lateinit var binding: DialogInvoiceViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogInvoiceViewBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_invoice_view,null))
        setContentView(binding.root)

        ///////////// get address from sharedPref

        binding.foodRecycler.adapter = InvoiceFoodAdapter(context,order.cart)

        binding.logo.setImageResource(R.drawable.poslogo)

        binding.address.text = context.getString(R.string.address)

        binding.date.text = Util.getDate().toString()

        binding.customerName.text = order.odrInf.csInf.csNm

        binding.counterName.text = "Daud"

        binding.tableNo.text = order.odrInf.tbl

        binding.orderId.text = order.id.toString()

        binding.printBtn.setOnClickListener {

        }
    }
}