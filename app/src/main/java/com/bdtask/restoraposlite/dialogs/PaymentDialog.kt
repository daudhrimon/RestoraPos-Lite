package com.bdtask.restoraposlite.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleCoroutineScope
import com.bdtask.restoraposlite.adapters.PaymentAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.models.Payments
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogPaymentBinding
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat

class PaymentDialog(
    context: Context,
    private val orderJson: String,
    private val lifecycleScope: LifecycleCoroutineScope
) : Dialog(context) {
    private var _binding: DialogPaymentBinding? = null
    private val dBinding get() = _binding!!
    private lateinit var order: Order
    private val disTypes = arrayOf("Amount", "Percentage (%)")
    private val sharedPref = SharedPref
    private var payments = mutableListOf<String>()
    private var terminals = mutableListOf<String>()
    private var banks = mutableListOf<String>()
    private var totalAmount = 0.0
    private var disType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        order = Gson().fromJson(orderJson, Order::class.java)
        super.onCreate(savedInstanceState)
        _binding = DialogPaymentBinding.inflate(layoutInflater)
        setContentView(dBinding.root)
        sharedPref.init(context)

        if (order.discount > 0.0) {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.discountEt.setText(order.discount.toString())
        }

        getTotalPrice()

        setPaymentHeaders()

        dBinding.spinDisType.adapter = ArrayAdapter(
            context,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            disTypes
        )

        dBinding.spinDisType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                disType = sPos
                setPaymentHeaders()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {/**/
            }
        }

        dBinding.closeBtn.setOnClickListener {
            dismiss()
        }

        dBinding.addDisBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.VISIBLE
        }

        dBinding.discountEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/**/
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setPaymentHeaders()
            }

            override fun afterTextChanged(p0: Editable?) {/**/
            }
        })

        dBinding.paymentBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.GONE
            dBinding.paymentLay.visibility = View.GONE
            dBinding.payPrintBtn.visibility = View.VISIBLE
            order.payments.add(
                Payments(
                    0,
                    "",
                    "",
                    "",
                    dBinding.totalDue.text.toString().toDouble()
                )
            )
            dBinding.totalDue.text = "0.0"
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context, dBinding.root)
        }

        dBinding.duePosBtn.setOnClickListener {
            if ((order.discount ?: 0.0) > 0.0) {
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabaseInstance(context.applicationContext).AppDao().updateOrder(order)
                }
            }
            dismiss()
            getRoundedTotal()
            val dialog = InvoiceViewDialog(context, order)
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width) / 15, (24 * height) / 25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dBinding.addAnotherPay.setOnClickListener {
            order.payments.add(
                Payments(
                    0,
                    "",
                    "",
                    "",
                    dBinding.totalDue.text.toString().toDouble()
                )
            )
            dBinding.totalDue.text = "0.0"
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context, dBinding.root)
            dBinding.addAnotherPay.visibility = View.GONE
        }

        dBinding.payPrintBtn.setOnClickListener {
            val totalDue = (dBinding.totalDue.text.toString() ?: "0.0").toDouble()
            if (totalDue == 0.0) {

                getRoundedTotal()

                if (order.status == 0) {
                    order.status = 1


                    lifecycleScope.launch(Dispatchers.IO) {

                        AppDatabase.getDatabaseInstance(context.applicationContext).AppDao().updateOrder(order)

                        withContext(Dispatchers.Main) {

                            Toasty.success(context, "Order Completed", Toasty.LENGTH_SHORT, true)
                                .show()

                            printInvoice()
                        }
                    }
                } else {


                    lifecycleScope.launch(Dispatchers.IO) {

                        val orderId = AppDatabase.getDatabaseInstance(context.applicationContext).AppDao().insertOrder(order)

                        withContext(Dispatchers.Main) {
                            if (orderId != null && orderId.toString().isNotEmpty()) {

                                order.id = orderId

                                Toasty.success(
                                    context,
                                    "Placed Order $orderId Successfully",
                                    Toasty.LENGTH_SHORT,
                                    true
                                ).show()

                                printInvoice()
                            }
                        }
                    }
                }
            } else {
                Toasty.info(
                    context,
                    "Please Complete Payment, Amount Left to Pay ${totalAmount-totalDue}",
                    Toasty.LENGTH_SHORT,
                    true
                ).show()
            }
        }

        /// printer init ///
        //initPrinter()
    }

    private fun getRoundedTotal() {
        order.subTotal = roundOfDecimal(order.subTotal)!!
        order.vatTotal = roundOfDecimal(order.vatTotal)!!
        order.chargeTotal = roundOfDecimal(order.chargeTotal)!!
        order.discount = roundOfDecimal(order.discount)!!
        order.grandTotal =
            roundOfDecimal((order.subTotal + order.vatTotal + order.chargeTotal) - order.discount)!!
    }

    private fun printInvoice() {
        dismiss()
        val dialog = InvoiceViewDialog(context, order)
        dialog.show()
        val width = context.resources.displayMetrics.widthPixels
        val height = context.resources.displayMetrics.heightPixels
        val win = dialog.window
        win!!.setLayout((14 * width) / 15, (24 * height) / 25)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun getTotalPrice() {
        // getting total Amount
        order.subTotal = 0.0
        for (i in order.cart.indices) {
            order.subTotal += order.cart[i].subTotal
            Log.wtf("Subtotal", order.subTotal.toString())
        }

        order.vatTotal = (order.subTotal * order.vat) / 100
        order.chargeTotal = (order.subTotal * order.charge) / 100
        totalAmount = order.subTotal + order.vatTotal + order.chargeTotal
        dBinding.totalAmount.text = roundOfDecimal(totalAmount).toString()

        dBinding.totalAmountCr.text = " $appCurrency"
        dBinding.totalDueCr.text = " $appCurrency"
        dBinding.payableAmountCr.text = " $appCurrency"
        dBinding.changeDueCr.text = " $appCurrency"
    }

    private fun setPaymentHeaders() {
        var totalDue = 0.0
        var discount = 0.0

        // get discount
        discount = if (dBinding.discountEt.text.toString().isNotEmpty()) {
            dBinding.discountEt.text.toString().toDouble()
        } else {
            0.0
        }

        // discount amount or percent calculation
        if (disType == 1) {
            discount *= (totalAmount / 100)
            order.discount = discount
            totalDue = totalAmount - discount
        } else {
            order.discount = discount
            totalDue = totalAmount - discount
        }

        dBinding.totalDue.text = roundOfDecimal(totalDue).toString()
        dBinding.payable.text = roundOfDecimal(totalDue).toString()
    }

    private fun setPaymentAdapter() {
        if (sharedPref.readPayList() != null) {
            payments = sharedPref.readPayList()!!
        }
        if (sharedPref.readTerminalList() != null) {
            terminals = sharedPref.readTerminalList()!!
        }
        if (sharedPref.readBankList() != null) {
            banks = sharedPref.readBankList()!!
        }

        dBinding.paymentRV.adapter = PaymentAdapter(
            context, order.payments, payments, terminals, banks,
            dBinding.totalDue, dBinding.payable, dBinding.changeDue, dBinding.addAnotherPay
        )
    }


    private fun roundOfDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}