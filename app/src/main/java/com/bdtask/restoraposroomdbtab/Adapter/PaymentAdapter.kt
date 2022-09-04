package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhPaymentBinding

class PaymentAdapter( private val context: Context,
                      private val pay: MutableList<Pay>,
                      private val Payments: MutableList<String>,
                      private val terminals: MutableList<String>,
                      private val banks: MutableList<String> ): RecyclerView.Adapter<PaymentAdapter.VHPay>() {

    inner class VHPay(binding: VhPaymentBinding): RecyclerView.ViewHolder(binding.root){
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHPay {
        return VHPay(VhPaymentBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_payment, parent, false)))
    }

    override fun onBindViewHolder(holder: VHPay, position: Int) {

        holder.binding.spinPayments.adapter = ArrayAdapter(context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, Payments)

        holder.binding.spinPayments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                if (holder.binding.spinPayments.selectedItem.toString() == "Card Payment"){
                    holder.binding.cardLay.visibility = View.VISIBLE
                    setCardLayAdapter(holder)
                } else {
                    holder.binding.cardLay.visibility = View.GONE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        holder.binding.payAmountEt.addTextChangedListener {

        }
    }

    private fun setCardLayAdapter(holder: VHPay) {
        holder.binding.spinTerminal.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, terminals)
        holder.binding.spinBank.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, banks)
    }

    override fun getItemCount(): Int {
        return pay.size
    }
}