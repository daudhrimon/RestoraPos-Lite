package com.bdtask.restoraposlite.dialogs

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
import com.bdtask.restoraposlite.models.Addon
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.printer.PrinterUtil.ESCUtil.boldOff
import com.bdtask.restoraposlite.printer.PrinterUtil.ESCUtil.boldOn
import com.bdtask.restoraposlite.printer.PrinterUtil.SunmiPrintHelper
import com.bdtask.restoraposlite.room.entity.Customer
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.Util
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

class InvoicePrintDialog(
    context: Context,
    private var order: Order,
    private val posLogo: String,
    private val resInf: Customer?,
    private val customerPay: Double,
    private val changeDue: Double,
    private val totalDue: Double
) : SweetAlertDialog(context, SUCCESS_TYPE) {

    private lateinit var printHelper: SunmiPrintHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleText = "Do You Want To Print Invoice ?"
        cancelText = "No"
        confirmText = "Yes"
        setCancelable(false)

        initPrinter()


        setCancelClickListener {
            dismissWithAnimation()
        }

        setConfirmClickListener {

            dismissWithAnimation()

            Glide.with(context)
                .asBitmap()
                .load(posLogo)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {

                        if (Util.getPrinterDevice(getDefaultAdapter()) == true) {

                            val sunmiPrinterService: SunmiPrinterService? =
                                printHelper.sunmiPrinterService

                            val txts = arrayOf("Daud", "Hoshen")
                            val width = intArrayOf(1, 1)
                            val align = intArrayOf(0, 2)
                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.printBitmap(resource, null)

                            sunmiPrinterService?.printText("\n", null)
                            //text print
                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.printTextWithFont(
                                "${resInf?.name}\n${resInf?.address}\n${resInf?.email}\n${resInf?.mobile}\n\n",
                                "",
                                25f,
                                null
                            )

                            sunmiPrinterService?.setAlignment(1, null)
                            sunmiPrinterService?.printTextWithFont(
                                "${order.date}\n", "",
                                25f,
                                null
                            )
                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Order: ${order.id}"
                            txts[1] = "Table: ${order.orderInfo.tbl}"
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
                                    itemName, null
                                )

                                sunmiPrinterService?.sendRAWData(boldOff(), null)

                                items[0] = "${order.cart[i].variant} x${order.cart[i].quantity}"
                                items[1] =
                                    "${order.cart[i].price * order.cart[i].quantity} $appCurrency"
                                sunmiPrinterService?.printColumnsString(
                                    items,
                                    width,
                                    align,
                                    null
                                )
                                if (order.cart[i].addon.isNotEmpty()) {
                                    val addonsList: List<Addon> = order.cart[i].addon
                                    for (k in addonsList.indices) {
                                        items[0] =
                                            "${addonsList[k].name} x${addonsList[k].quantity}"
                                        items[1] = "${addonsList[k].price} $appCurrency"
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
                            txts[0] = "Subtotal:"
                            txts[1] = "${order.subTotal} $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align,
                                null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Vat/Tax (${order.vat}%):"
                            txts[1] = "${order.vatTotal} $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align,
                                null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Service Crg (${order.charge}%):"
                            txts[1] = "${order.chargeTotal} $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align,
                                null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Discount:"
                            txts[1] = "${order.discount} $appCurrency"
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

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Grand Total:"
                            txts[1] = "${order.grandTotal} $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align, null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Total Due: "
                            txts[1] = "$totalDue $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align, null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Change Due: "
                            txts[1] = "$changeDue $appCurrency"
                            sunmiPrinterService?.printColumnsString(
                                txts,
                                width,
                                align, null
                            )

                            sunmiPrinterService?.setFontSize(23f, null)
                            txts[0] = "Customer Paid:"
                            txts[1] = "$customerPay $appCurrency"
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
                                "Powered by Restora POS\n", 22f, false, false,
                                null
                            )

                            SunmiPrintHelper.getInstance().feedPaper()

                        } else {

                            // Print By Bluetooth
                            if (ContextCompat.checkSelfPermission(
                                    context.applicationContext,
                                    Manifest.permission.BLUETOOTH
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    ownerActivity!!.parent,
                                    arrayOf(Manifest.permission.BLUETOOTH),
                                    1
                                )
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
                                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                                            printer,
                                            resource
                                        )
                                                + "</img>\n\n" +
                                                "[C]${resInf?.name}\n" +
                                                "[C]${resInf?.address}\n" +
                                                "[C]Email: ${resInf?.email}\n" +
                                                "[C]Mobile: ${resInf?.mobile}\n" +
                                                "[L]\n" +
                                                "[C]${order.date}\n" +
                                                "[L]Order: ${order.id}" + "[R]Table: ${order.orderInfo.tbl}\n" +
                                                "[C]================================\n" +
                                                loopData(order.cart) +
                                                "[C]================================\n" +
                                                "[L]Subtotal: " + "[R]${order.subTotal} $appCurrency\n" +
                                                "[L]Vat/Tax (${order.vat}%):" + "[R]${order.vatTotal} $appCurrency\n" +
                                                "[L]Service Crg (${order.charge}%):" + "[R]${order.chargeTotal} $appCurrency\n" +
                                                "[L]Discount: " + "[R]${order.discount} $appCurrency\n" +
                                                "[C]================================\n" +
                                                "[L]Grand Total: " + "[R]${order.grandTotal} $appCurrency\n" +
                                                "[L]Total Due: " + "[R]$totalDue $appCurrency\n" +
                                                "[L]Change Due: " + "[R]$changeDue $appCurrency\n" +
                                                "[L]Customer Paid: " + "[R]$customerPay $appCurrency\n" +
                                                "[C]================================\n" +
                                                "[C] <b> Thank you very much </b>\n" +
                                                "[C] <b>Powered by Restora POS</b>\n" + "\n\n"
                                    )

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

                    override fun onLoadCleared(placeholder: Drawable?) {/**/
                    }
                })
        }
    }

    private fun initPrinter() {
        if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {
            SunmiPrintHelper.getInstance().initSunmiPrinterService(context)
            printHelper = SunmiPrintHelper.getInstance()
            printHelper.initSunmiPrinterService(context)
        }
    }

    private fun loopData(list: MutableList<Cart>): String {
        var items = ""
        for (i in list.indices) {
            items =
                "$items[L]<b>${list[i].title}</b>\n[L]${list[i].variant} x${list[i].quantity}[R]<b>${list[i].price} $appCurrency</b>\n"
            if (list[i].addon.isNotEmpty()) {
                val addonsList: List<Addon> = list[i].addon
                for (k in addonsList.indices) {
                    items =
                        "$items[L]${addonsList[k].name} x${addonsList[k].quantity} [R]${addonsList[k].price} $appCurrency\n"
                }
            }
        }
        return items
    }
}