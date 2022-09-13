package com.bdtask.restoraposlite.Dialog

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bdtask.restoraposlite.Interface.TokenClickListener
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.Model.OdrInf
import com.bdtask.restoraposlite.Printer.PrinterUtil.ESCUtil
import com.bdtask.restoraposlite.Printer.PrinterUtil.SunmiPrintHelper
import com.bdtask.restoraposlite.Util.Util
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.exceptions.EscPosEncodingException
import com.dantsu.escposprinter.exceptions.EscPosParserException
import com.sunmi.peripheral.printer.SunmiPrinterService
import es.dmoral.toasty.Toasty

class TokenPrintDialog(context: Context,
                       private val token: String,
                       private val orderId: Long?,
                       private val cartList: MutableList<Cart>,
                       private val odrInf: OdrInf,
                       private val tokenClickListener: TokenClickListener ): SweetAlertDialog(context, SUCCESS_TYPE) {

    private lateinit var printHelper: SunmiPrintHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleText = "Do you want to print Token ?"
        confirmText = "Yes"
        cancelText = "No"

        initPrinter()

        setCancelClickListener {
            tokenClickListener.onTokenButtonsClick(this)
        }

        setConfirmClickListener {

            tokenClickListener.onTokenButtonsClick(this)

            if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {

                val sunmiPrinterService: SunmiPrinterService? = printHelper.sunmiPrinterService

                //Sunmi Printer

                val txt = arrayOf("Daud", "Hoshen")
                val width = intArrayOf(1, 1)
                val align = intArrayOf(0, 2)
                sunmiPrinterService!!.setAlignment(1, null)
                sunmiPrinterService!!.printTextWithFont("Token :$token\b\n", null, 42f, null)

                txt[0] = odrInf.csInf.csNm
                txt[1] = odrInf.wtr
                sunmiPrinterService.printColumnsString(
                    txt,
                    width, align, null
                )

                sunmiPrinterService.printText("\n", null)

                sunmiPrinterService.sendRAWData(ESCUtil.boldOn(), null)
                val itemss = arrayOf("Items","Size")
                sunmiPrinterService.printColumnsString(
                    itemss,
                    width, align, null
                )
                sunmiPrinterService!!.printTextWithFont("\n", null, 28f, null)

                val items = arrayOf("", "")
                sunmiPrinterService!!.setAlignment(0, null)
                for (i in cartList.indices) {

                    sunmiPrinterService.sendRAWData(ESCUtil.boldOn(), null)

                    sunmiPrinterService!!.printTextWithFont(
                        cartList[i].title + "\n",
                        null,
                        28f,
                        null
                    )
                    sunmiPrinterService.setFontSize(26f,null)
                    sunmiPrinterService.sendRAWData(ESCUtil.boldOff(), null)
                    val items = arrayOf("x"+cartList[i].fQnty,cartList[i].vari )
                    sunmiPrinterService.printColumnsString(
                        items,
                        width, align, null
                    )

                    if (cartList[i].adns.size > 0) {
                        val addonsList = cartList[i].adns

                        for (k in addonsList.indices) {
                            val addonItem = arrayOf(addonsList[k].adnNm,addonsList[k].adnQnty.toString())
                            sunmiPrinterService.printColumnsString(
                                addonItem,
                                width, align, null)
                        }
                    }

                    if (cartList[i].nt.isNotEmpty()){
                        sunmiPrinterService.printTextWithFont(
                            cartList[i].nt+"\n" , null, 28f, null
                        )
                    }

                }
                sunmiPrinterService.printTextWithFont("\n", null, 28f, null)
                sunmiPrinterService.sendRAWData(ESCUtil.boldOn(), null)
                if (odrInf.tbl.isNotEmpty()) {
                    items[0] = "Order: $orderId"
                    items[1] = "Table: ${odrInf.tbl}"
                    sunmiPrinterService.printColumnsString(
                        items,
                        width, align, null
                    )
                }else{
                    items[0] = "Order: $orderId"
                    items[1] = ""
                    sunmiPrinterService.printColumnsString(
                        items, width, align, null
                    )
                }

                sunmiPrinterService.printText("\n", null)

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
                            "[C]<b><font size='big'>Token : $token </font></b>\n"+
                                    "[L]\n"+
                                    "[L]${odrInf.csInf.csNm} [R]${odrInf.wtr}\n"+
                                    "[L]\n"+
                                    "[L]<b>Items</b> [R]<b>Size</b>\n"+
                                    "[L]\n"+
                                    tokenLoopData(cartList)+
                                    "[L]\n"+
                                    "[L]Order: $orderId [R]Table: ${odrInf.tbl}")
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
    }

    private fun initPrinter() {
        if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {
            SunmiPrintHelper.getInstance().initSunmiPrinterService(context)
            printHelper = SunmiPrintHelper.getInstance()
            printHelper.initSunmiPrinterService(context)
        }
    }

    // token loop Data
    private fun tokenLoopData(list: MutableList<Cart>): String {
        var items = ""
        for (i in list.indices) {
            items = "$items[L]<b>${list[i].title}</b>\n"+
                    "[L]x${list[i].fQnty} [R]<b>${list[i].vari}</b>\n"

            if (list[i].adns.size > 0) {
                val addonsList = list[i].adns
                for (k in addonsList.indices) {
                    items = "$items[L]${addonsList[k].adnNm}x${addonsList[k].adnQnty}\n"
                }
            }

            if  (list[i].nt != ""){
                items = "$items[L]<b>${list[i].title}</b>\n"
            }
        }
        return items
    }
}