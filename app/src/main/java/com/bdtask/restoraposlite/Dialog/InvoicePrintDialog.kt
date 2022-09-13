package com.bdtask.restoraposlite.Dialog

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.getDefaultAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.Model.Adns
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.Model.Pay
import com.bdtask.restoraposlite.Printer.PrinterUtil.ESCUtil.boldOff
import com.bdtask.restoraposlite.Printer.PrinterUtil.ESCUtil.boldOn
import com.bdtask.restoraposlite.Printer.PrinterUtil.SunmiPrintHelper
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.exceptions.EscPosEncodingException
import com.dantsu.escposprinter.exceptions.EscPosParserException
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.sunmi.peripheral.printer.SunmiPrinterService
import es.dmoral.toasty.Toasty

class InvoicePrintDialog(context: Context,
                         private var order: Order,
                         private val posLogo: String,
                         private val resInf: Cstmr?) : SweetAlertDialog(context, SUCCESS_TYPE) {

    private lateinit var printHelper: SunmiPrintHelper
    private var vat = 0.0
    private var crg = 0.0
    private var grandTotal = 0.0
    private var customerPay = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleText = "Do You Want To Print Invoice ?"
        cancelText = "No"
        confirmText = "Yes"

        vat = (order.tPrc*order.vat)/100
        crg = (order.tPrc*order.crg)/100
        customerPay = getCustomerPay(order.pay)
        grandTotal = (order.tPrc+vat+crg)-order.dis


        setCancelClickListener {
            dismissWithAnimation()
        }

        setConfirmClickListener {

            dismissWithAnimation()

            Glide.with(context)
                .asBitmap()
                .load(posLogo)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                        if (Util.getPrinterDevice(getDefaultAdapter()) == true) {
                            SunmiPrintHelper.getInstance().initSunmiPrinterService(context)
                            printHelper = SunmiPrintHelper.getInstance()
                            printHelper.initSunmiPrinterService(context)

                            val sunmiPrinterService: SunmiPrinterService? = printHelper.sunmiPrinterService

                            val txts = arrayOf("Daud", "Hoshen")
                            val width = intArrayOf(1, 1)
                            val align = intArrayOf(0, 2)
                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.printBitmap(resource, null)

                            sunmiPrinterService?.printText("\n", null)
                            //text print
                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.printTextWithFont(
                                "${order.dat}\n", "",
                                25f,
                                null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Order: ${order.id}"
                            txts[1] = "Table: ${order.odrInf.tbl}"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align,
                                null
                            )
                            sunmiPrinterService?.printText(
                                "================================\n",
                                null
                            )
                            val items = arrayOf("", "")
                            var adOnPrice = 0.0
                            for (i in order.cart.indices) {
                                sunmiPrinterService?.setAlignment(0, null)
                                sunmiPrinterService?.sendRAWData(boldOn(), null)
                                val itemName = "${order.cart[i].title}\n"
                                sunmiPrinterService?.printText(
                                    itemName, null)

                                sunmiPrinterService?.sendRAWData(boldOff(), null)

                                items[0] = order.cart[i].vari + " x" + order.cart[i].fQnty + ""
                                items[1] = "${order.cart[i].fPrc * order.cart[i].fQnty}"
                                sunmiPrinterService?.printColumnsString(
                                    items,
                                    width,
                                    align,
                                    null
                                )
                                if (order.cart[i].adns.isNotEmpty()) {
                                    val addonsList: List<Adns> = order.cart[i].adns

                                    for (k in addonsList.indices) {
                                        items[0] = "${addonsList[k].adnNm} x${addonsList[k].adnQnty}"
                                        items[1] = addonsList[k].adnPrc.toString()
                                        sunmiPrinterService?.printColumnsString(
                                            items,
                                            width,
                                            align,
                                            null
                                        )
                                    }
                                }

                            }
                            sunmiPrinterService?.printText(
                                "================================\n",
                                null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Subtotal: "
                            txts[1] = order.tPrc.toString()
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align,
                                null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)

                            val sc = arrayOf(
                                "Service charge: 0"
                            )
                            sunmiPrinterService?.printColumnsString(
                                sc,
                                width,
                                align, null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            val vat = arrayOf("VAT: 0")
                            sunmiPrinterService?.printColumnsString(
                                vat,
                                width,
                                align, null
                            )
                            sunmiPrinterService?.printText(
                                "================================\n",
                                null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Total:"
                            txts[1] = order.tPrc.toString()
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align, null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Discount:"
                            txts[1] = order.dis.toString()
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align, null
                            )

                                sunmiPrinterService?.setFontSize(23f, null)
                                txts[0] = "Customer Paid:"
                                txts[1] = ""
                                sunmiPrinterService?.printColumnsString(
                                    txts,
                                    width,
                                    align, null
                                )

                                sunmiPrinterService?.setFontSize(23f, null)
                                txts[0] = "Change Due: "
                                txts[1] = ""
                                sunmiPrinterService?.printColumnsString(
                                    txts,
                                    width,
                                    align, null
                                )

                            sunmiPrinterService?.printText(
                                "================================\n",
                                null
                            )
                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.setFontSize(23f, null)
                            sunmiPrinterService?.printText(
                                "Thank you very much\n",
                                null
                            )
                            SunmiPrintHelper.getInstance().printText(
                                "Powered by\nRestora POS\n", 22f, false, false,
                                null
                            )

                            SunmiPrintHelper.getInstance().feedPaper()

                        } else {

                            // Print By Bluetooth
                            if (ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ownerActivity!!.parent, arrayOf(Manifest.permission.BLUETOOTH),1)
                            } else {

                                //ESCPOS-ThermalPrinter

                                var printer: EscPosPrinter? = null

                                try {
                                    printer = EscPosPrinter(
                                        BluetoothPrintersConnections.selectFirstPaired(),
                                        203, 48f, 32, EscPosCharsetEncoding("windows-1252", 16)
                                    )
                                } catch (e: EscPosConnectionException) {
                                    e.printStackTrace()
                                }
                                try {
                                    printer!!.printFormattedTextAndCut(
                                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,resource)
                                                + "</img>\n" +
                                                "[L]\n" +
                                                "[C]${resInf?.nm ?: "RestoraPOS Lite"}\n" +
                                                "[C]${resInf?.adrs ?: "MannanPlaza,Khilkhet,Dhaka-1215"}\n" +
                                                "[C]Email: ${resInf?.eml ?: "bdtask@gmail.com"}\n" +
                                                "[C]Mobile: ${resInf?.mbl ?: "0123456789"}\n" +
                                                "[L]\n" +
                                                "[C]Date: ${order.dat}\n" +
                                                "[L]Order: ${order.id}" + "[R]Table: ${order.odrInf.tbl}\n" +
                                                "[C]================================\n" +
                                                loopData(order.cart)+
                                                "[C]================================\n" +
                                                "[L]Subtotal: " + "[R]${order.tPrc} $appCurrency\n" +
                                                "[L]VAT: " + "[R]$vat\n" +
                                                "[L]Service charge: " + "[R]$crg $appCurrency\n" +
                                                "[L]Discount: " + "[R]${order.dis} $appCurrency\n" +
                                                "[C]================================\n" +
                                                "[L]Grand Total: " + "[R]$grandTotal $appCurrency\n" +
                                                "[L]Total Due: " + "[R]${getTotalDue()} $appCurrency\n" +
                                                "[L]Change Due: " + "[R]${getChangeDue()} $appCurrency\n" +
                                                "[L]Customer Paid: " + "[R]$customerPay $appCurrency\n" +
                                                "[C] <b> Thank you very much </b>\n" +
                                                "[C]================================\n" +
                                                "[C] <b>Powered by ***Restora POS***</b>\n" + "\n\n")

                                } catch (e: EscPosConnectionException) {
                                    e.printStackTrace()
                                } catch (e: EscPosParserException) {
                                    e.printStackTrace()
                                } catch (e: EscPosEncodingException) {
                                    e.printStackTrace()
                                } catch (e: EscPosBarcodeException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {/**/}
                })
        }
    }

    private fun getTotalDue(): String {
        var ttlDue = 0.0
        return if (grandTotal > customerPay){
            ttlDue = grandTotal-customerPay
            if (ttlDue < 0){
                ttlDue = 0.0
                return ttlDue.toString()
            } else {
                ttlDue.toString()
            }
        } else {
            ttlDue.toString()
        }
    }

    private fun getChangeDue(): String {
        var cngDue = 0.0
        return if (customerPay > grandTotal){
            cngDue = customerPay - grandTotal
            cngDue.toString()
        } else {
            cngDue.toString()
        }
    }

    private fun getCustomerPay(pay: MutableList<Pay>): Double {
        var csPay = 0.0
        return if(order.pay.isNotEmpty()){
            for (i in order.pay.indices){
                csPay += order.pay[i].amo
            }
            csPay
        } else {
            csPay
        }
    }

    private fun loopData(list:MutableList<Cart>): String {
        var items = ""
        for (i in list.indices) {
            items = "$items[L]<b>${list[i].title}</b>\n[L]${list[i].vari} x${list[i].fQnty}[R]<b>${list[i].fPrc} $appCurrency</b>\n"
            if (list[i].adns.isNotEmpty()) {
                val addonsList: List<Adns> = list[i].adns
                for (k in addonsList.indices) {
                    items = "$items[L]${addonsList[k].adnNm} x${addonsList[k].adnQnty} [R]${addonsList[k].adnPrc} $appCurrency\n"
                }
            }
        }
        return items
    }
}