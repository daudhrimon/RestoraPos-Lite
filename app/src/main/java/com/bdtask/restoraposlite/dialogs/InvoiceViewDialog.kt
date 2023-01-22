package com.bdtask.restoraposlite.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.bdtask.restoraposlite.adapters.InvoiceFoodAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.models.Payments
import com.bdtask.restoraposlite.room.entity.Customer
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogInvoiceViewBinding
import com.bumptech.glide.Glide
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import es.dmoral.toasty.Toasty

class InvoiceViewDialog(context: Context, private val order: Order) : Dialog(context) {
    private var _binding: DialogInvoiceViewBinding? = null
    private val binding get() = _binding!!
    private val sharedPref = SharedPref
    private var resInf: Customer? = null
    private var posLogo: String? = null
    private var customerPay = 0.0
    private var changeDue = 0.0;
    private var totalDue = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        super.onCreate(savedInstanceState)
        _binding = DialogInvoiceViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        posLogo = sharedPref.readPosLogo() ?: ""
        resInf = sharedPref.readResInf()

        Glide.with(context).asBitmap().load(posLogo).into(binding.logo)

        binding.address.text = "${resInf?.name}\n${resInf?.address}\n${resInf?.email}\n${resInf?.mobile}"

        getCustomerPay(order.payments)

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        ///////////// get address from sharedPref

        binding.foodRecycler.adapter = InvoiceFoodAdapter(context, order.cart)

        binding.date.text = Util.getDate().toString()

        binding.customerName.text = order.orderInfo.customerInfo.csNm

        binding.oprName.text = order.operator

        binding.subTotal.text = "${order.subTotal} $appCurrency"


        binding.vatTxTv.text = "Vat/Tax (${order.vat}%)"
        binding.vatTv.text = "${order.vatTotal} $appCurrency"


        binding.sCrgTv.text = "Service Crg (${order.charge}%)"
        binding.chargeTv.text = "${order.chargeTotal} $appCurrency"

        binding.discountTv.text = "${order.discount} $appCurrency"

        binding.grandtotalTV.text = "${order.grandTotal} $appCurrency"

        totalDue = getTotalDue()
        binding.totalDue.text = "$totalDue $appCurrency"

        changeDue = getChangeDue()
        binding.changeDue.text = "$changeDue $appCurrency"

        binding.customerPaid.text = "$customerPay $appCurrency"

        binding.tableNo.text = order.orderInfo.tbl

        binding.orderId.text = order.id.toString()



        binding.printBtn.setOnClickListener {
            if (posLogo != null && posLogo!!.isNotEmpty()) {
                InvoicePrintDialog(
                    context,
                    order,
                    posLogo!!,
                    resInf,
                    customerPay,
                    changeDue,
                    totalDue
                ).show()
                dismiss()
            } else {
                Toasty.warning(
                    context, "You Can't Print invoice Without a POS Logo", Toasty.LENGTH_SHORT, true
                ).show()
            }
        }

        binding.shareBtn.setOnClickListener {

            PdfGenerator.getBuilder()
                .setContext(context)
                .fromViewSource()
                .fromView(binding.content)
                .setFileName("${sharedPref.readResInf()?.name}, Order_No-${order.id}, Date-${order.date}, Billing_to-${order.orderInfo.customerInfo.csNm}")
                .setFolderNameOrPath("${resInf?.name} Invoices")
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
                .build(object : PdfGeneratorListener() {
                    override fun onFailure(failureResponse: FailureResponse) {
                        super.onFailure(failureResponse)
                        Log.wtf("FAIL","")
                    }
                    override fun showLog(log: String) {
                        super.showLog(log)
                        Log.wtf("LOG","")
                    }
                    override fun onStartPDFGeneration() {
                        Log.wtf("Start","")
                    }
                    override fun onFinishPDFGeneration() {
                        Log.wtf("Finish","")
                    }
                    override fun onSuccess(response: SuccessResponse) {
                        super.onSuccess(response)
                        Log.wtf("Success","")
                    }
                })
        }
    }

    private fun getCustomerPay(payments: MutableList<Payments>) {
        if (payments.isNotEmpty()) {
            for (i in payments.indices) {
                customerPay += payments[i].amo
            }
        }
    }

    private fun getTotalDue(): Double {
        var ttlDue = 0.0
        return if (order.grandTotal > customerPay) {
            ttlDue = order.grandTotal - customerPay
            if (ttlDue < 0) {
                ttlDue = 0.0
                return ttlDue
            } else {
                ttlDue
            }
        } else {
            ttlDue
        }
    }

    private fun getChangeDue(): Double {
        var cngDue = 0.0
        return if (customerPay > order.grandTotal) {
            cngDue = customerPay - order.grandTotal
            cngDue
        } else {
            cngDue
        }
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}