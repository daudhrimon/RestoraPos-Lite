package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.PayAmountTextChangedListener
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhPaymentBinding
import com.google.android.material.navigation.NavigationBarView

class PaymentAdapter( private val context: Context,
                      private val payList: MutableList<Pay>,
                      private val Payments: MutableList<String>,
                      private val terminals: MutableList<String>,
                      private val banks: MutableList<String>,
                      private val payAmountTextChangeListener: PayAmountTextChangedListener ): RecyclerView.Adapter<PaymentAdapter.VHPay>() {

    inner class VHPay(binding: VhPaymentBinding): RecyclerView.ViewHolder(binding.root){
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHPay {
        return VHPay(VhPaymentBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_payment, parent, false)))
    }

    override fun onBindViewHolder(holder: VHPay, position: Int) {
        val adapterPos: Int = position

        holder.binding.payAmountEt.setText(payList[position].amo.toString())

        if (position != 0){
            holder.binding.dltPayBtn.visibility = View.VISIBLE

            if (position == payList.size-1){
                holder.binding.payAmountEt.requestFocus()
            }
        }

        holder.binding.spinPayments.adapter = ArrayAdapter(context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, Payments)

        holder.binding.spinPayments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                if (holder.binding.spinPayments.selectedItem.toString() == "Card Payment"){
                    holder.binding.cardLay.visibility = View.VISIBLE
                    setCardLayAdapter(holder)
                    payList[adapterPos].typ = 1
                } else {
                    holder.binding.cardLay.visibility = View.GONE
                    payList[adapterPos].typ = 0
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        holder.binding.spinTerminal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                payList[adapterPos].ter = holder.binding.spinTerminal.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        holder.binding.spinBank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                payList[adapterPos].bnk = holder.binding.spinBank.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        holder.binding.payAmountEt.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/**/}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()){
                    payList[adapterPos].amo = p0.toString().toDouble()
                } else {
                    payList[adapterPos].amo = 0.0
                }
                payAmountTextChangeListener.onPayAmountTextChange()
            }
            override fun afterTextChanged(p0: Editable?) {/**/}
        })

        holder.binding.dltPayBtn.setOnClickListener {
            payList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    private fun setCardLayAdapter(holder: VHPay) {
        holder.binding.spinTerminal.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, terminals)
        holder.binding.spinBank.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, banks)
    }

    override fun getItemCount(): Int {
        return payList.size
    }
}