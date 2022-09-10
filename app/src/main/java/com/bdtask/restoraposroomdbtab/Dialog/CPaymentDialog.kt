package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.Adapter.PaymentAdapter
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.appCurrency
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.Printer.PrinterUtil.SunmiPrintHelper
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogPaymentBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CPaymentDialog (context: Context): Dialog(context) {

    private lateinit var dBinding: DialogPaymentBinding
    private lateinit var order: Order
    private val disTypes = arrayOf("Amount", "Percentage (%)")
    private val sharedPref = SharedPref
    private var payments = mutableListOf<String>()
    private var terminals = mutableListOf<String>()
    private var banks = mutableListOf<String>()
    private var totalAmount = 0.0
    private var disType = 0
    private lateinit var printHelper: SunmiPrintHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        order = sharedPref.readOrder()!!
        super.onCreate(savedInstanceState)
        dBinding = DialogPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_payment,null))
        setContentView(dBinding.root)

        getTotalPrice()

        setPaymentHeaders()

        dBinding.spinDisType.adapter = ArrayAdapter(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,disTypes)

        dBinding.spinDisType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                disType = sPos
                setPaymentHeaders()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        dBinding.closeBtn.setOnClickListener {
            dismiss()
        }

        dBinding.addDisBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.VISIBLE
        }

        dBinding.discountEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/**/}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setPaymentHeaders()
            }
            override fun afterTextChanged(p0: Editable?) {/**/}
        })

        dBinding.paymentBtn.setOnClickListener {
            dBinding.addDisBtn.visibility = View.GONE
            dBinding.distLay.visibility = View.GONE
            dBinding.paymentBtn.visibility = View.GONE
            dBinding.payPrintBtn.visibility = View.VISIBLE
            order.pay.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,dBinding.root)
        }

        dBinding.addAnotherPay.setOnClickListener {
            order.pay.add(Pay(0,"","","",0.0))
            setPaymentAdapter()
            Util.hideSoftKeyBoard(context,dBinding.root)
        }

        dBinding.payPrintBtn.setOnClickListener {
            val payable = dBinding.payableAmount.text.toString().toDouble()
            if (payable == 0.0){

                if (order.sts == 0){
                    order.sts = 1

                    GlobalScope.launch(Dispatchers.IO) {

                        MainActivity.database.orderDao().updateOrder(order)

                        withContext(Dispatchers.Main){

                            sharedPref.writeOrder(order)

                            Toasty.success(context,"Order Completed",Toasty.LENGTH_SHORT).show()

                            printInvoice()
                        }
                    }
                } else {

                    GlobalScope.launch(Dispatchers.IO){

                        val orderId = MainActivity.database.orderDao().insertOrder(order)

                        withContext(Dispatchers.Main){
                            if (orderId != null && orderId.toString().isNotEmpty()) {

                                order.id = orderId

                                sharedPref.writeOrder(order)

                                Toasty.success(context, "Placed Order $orderId Successfully",Toasty.LENGTH_SHORT,true).show()

                                printInvoice()
                            }
                        }
                    }
                }
            } else {
                Toasty.info(context,"Please Complete Payment, Amount Left to Pay ${totalAmount-payable}",Toasty.LENGTH_SHORT).show()
            }
        }

        /// printer init ///
        //initPrinter()
    }

    private fun printInvoice(){
        val dialog = InvoiceViewDialog(context,1)
        dialog.show()
        val width = context.resources.displayMetrics.widthPixels
        val height = context.resources.displayMetrics.heightPixels
        val win = dialog.window
        win!!.setLayout((14 * width)/15,(24 * height)/25)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dismiss()
    }

    private fun getTotalPrice() {
        // getting total Amount
        for (i in order.cart.indices) {
            order.tPrc += order.cart[i].tUPrc
        }
        val vat = (order.tPrc * order.vat) / 100
        val crg = (order.tPrc * order.crg) / 100
        totalAmount = order.tPrc + vat + crg
        dBinding.totalAmount.text = totalAmount.toString()

        dBinding.totalAmountCr.text = " $appCurrency"
        dBinding.totalDueCr.text = " $appCurrency"
        dBinding.payableAmountCr.text = " $appCurrency"
        dBinding.changeDueCr.text = " $appCurrency"
    }

    private fun setPaymentHeaders() {
        var totalDue = 0.0
        var discount = 0.0

        // get discount
        discount = if (dBinding.discountEt.text.toString().isNotEmpty()){
            dBinding.discountEt.text.toString().toDouble()
        } else {
            0.0
        }

        // discount amount or percent calculation
        if (disType == 1) {
            discount *= (totalAmount / 100)
            order.dis = discount
            totalDue = totalAmount - discount
        } else {
            order.dis = discount
            totalDue = totalAmount - discount
        }

        dBinding.totalDue.text = totalDue.toString()
        dBinding.payableAmount.text = totalDue.toString()
    }

    private fun setPaymentAdapter() {
        if (sharedPref.readPayList() != null){
            payments = sharedPref.readPayList()!!
        }
        if (sharedPref.readTerminalList() != null){
            terminals = sharedPref.readTerminalList()!!
        }
        if (sharedPref.readBankList() != null){
            banks = sharedPref.readBankList()!!
        }

        dBinding.paymentRV.adapter = PaymentAdapter(context, order.pay, payments, terminals, banks,
            dBinding.totalDue,dBinding.payableAmount,dBinding.changeDue,dBinding.addAnotherPay)
    }

    // init Printer
    private fun initPrinter() {
        if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {
            SunmiPrintHelper.getInstance().initSunmiPrinterService(context)
            printHelper = SunmiPrintHelper.getInstance()
            printHelper.initSunmiPrinterService(context)
        }
    }
}