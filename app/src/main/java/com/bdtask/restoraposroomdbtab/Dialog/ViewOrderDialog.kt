package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.DialogViewOrderBinding

class ViewOrderDialog(context: Context, private val order: Order): Dialog(context) {
    private var _binding: DialogViewOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogViewOrderBinding.bind(layoutInflater.inflate(R.layout.dialog_view_order,null))
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        binding.orderId.text = order.id.toString()
        if (order.tkn.isNotEmpty()){
            binding.tokenLay.visibility = View.VISIBLE
            binding.token.text = order.tkn
        }
        binding.date.text = order.dat

        binding.cusName.text = order.odrInf.csInf.csNm
        binding.address.text = order.odrInf.csInf.csAdrs
        binding.mobile.text = order.odrInf.csInf.mbl



    }
}
