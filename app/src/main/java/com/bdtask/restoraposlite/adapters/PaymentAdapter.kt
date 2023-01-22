package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.models.Payments
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhPaymentBinding
import es.dmoral.toasty.Toasty
import java.math.RoundingMode
import java.text.DecimalFormat

class PaymentAdapter(
    private val context: Context,
    private val paymentsList: MutableList<Payments>,
    private val Payments: MutableList<String>,
    private val terminals: MutableList<String>,
    private val banks: MutableList<String>,
    private val dTotalDue: TextView,
    private val dPayable: TextView,
    private val dChangeDue: TextView,
    private val dAnotherPay: TextView
) : RecyclerView.Adapter<PaymentAdapter.VHPay>() {

    inner class VHPay(val binding: VhPaymentBinding) : RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHPay {
        return VHPay(
            VhPaymentBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_payment, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VHPay, position: Int) {
        val adapterPos: Int = position

        holder.binding.payAmountEt.setText(paymentsList[position].amo.toString())

        if (position != 0) {
            holder.binding.dltPayBtn.visibility = View.VISIBLE
        }

        holder.binding.spinPayments.adapter = ArrayAdapter(
            context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, Payments
        )


        if (paymentsList[position].pay.isNotEmpty()) {
            for (i in Payments.indices) {
                if (Payments[i] == paymentsList[position].pay) {
                    holder.binding.spinPayments.setSelection(i)
                }
            }
        }


        holder.binding.spinPayments.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                    if (sPos == 1) {
                        if (terminals.isEmpty() || banks.isEmpty()){
                            Toasty.info(context,"Terminals or Banks not Found",Toasty.LENGTH_SHORT).show()
                        } else {
                            holder.binding.cardLay.visibility = View.VISIBLE
                            paymentsList[adapterPos].typ = 1
                            paymentsList[adapterPos].pay = holder.binding.spinPayments.selectedItem.toString()
                            setCardLayAdapter(holder, adapterPos)
                        }
                    } else {
                        holder.binding.cardLay.visibility = View.GONE
                        paymentsList[adapterPos].typ = 0
                        paymentsList[adapterPos].pay = holder.binding.spinPayments.selectedItem.toString()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }

        holder.binding.payAmountEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/**/
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0.toString().isNotEmpty()) {
                    paymentsList[adapterPos].amo = p0.toString().toDouble()
                } else {
                    paymentsList[adapterPos].amo = 0.0
                }

                val totalDue: Double
                var changeDue = 0.0
                val payable = dPayable.text.toString().toDouble()

                var adAmount = 0.0
                for (i in paymentsList.indices) {
                    adAmount += paymentsList[i].amo
                }

                if (adAmount > payable) {
                    totalDue = 0.0
                    changeDue = adAmount - payable
                } else {
                    totalDue = payable - adAmount
                }

                dTotalDue.text = roundOfDecimal(totalDue).toString()
                dChangeDue.text = roundOfDecimal(changeDue).toString()

                if (totalDue > 0.0) {
                    dAnotherPay.visibility = View.VISIBLE
                } else {
                    dAnotherPay.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {/**/
            }
        })

        holder.binding.dltPayBtn.setOnClickListener {
            paymentsList[position - 1].amo += paymentsList[position].amo
            paymentsList.removeAt(position)
            notifyDataSetChanged()
        }
    }


    private fun roundOfDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }


    private fun setCardLayAdapter(holder: VHPay, adapterPos: Int) {
        holder.binding.spinTerminal.adapter = ArrayAdapter(
            context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            terminals
        )
        holder.binding.spinBank.adapter = ArrayAdapter(
            context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            banks
        )

        if (paymentsList[adapterPos].ter.isNotEmpty()) {
            for (i in terminals.indices) {
                if (terminals[i] == paymentsList[adapterPos].ter) {
                    holder.binding.spinTerminal.setSelection(i)
                }
            }
        }

        if (paymentsList[adapterPos].bnk.isNotEmpty()) {
            for (i in banks.indices) {
                if (banks[i] == paymentsList[adapterPos].bnk) {
                    holder.binding.spinBank.setSelection(i)
                }
            }
        }


        holder.binding.spinTerminal.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                    paymentsList[adapterPos].ter =
                        holder.binding.spinTerminal.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }

        holder.binding.spinBank.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                    paymentsList[adapterPos].bnk = holder.binding.spinBank.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }


    }

    override fun getItemCount(): Int {
        return paymentsList.size
    }
}