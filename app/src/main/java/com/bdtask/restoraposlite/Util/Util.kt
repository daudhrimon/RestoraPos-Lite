package com.bdtask.restoraposlite.Util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object Util {



    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }



    @SuppressLint("SimpleDateFormat")
    fun getDate(): String?{
        return SimpleDateFormat("dd-MM-yyyy").format(Date()).toString()
    }



    @SuppressLint("MissingPermission")
    fun getPrinterDevice(bluetoothAdapter: BluetoothAdapter): Boolean? {
        var innerprinter_device: BluetoothDevice? = null
        val devices = bluetoothAdapter.bondedDevices

        for (device in devices) {

            if (device.address == "00:11:22:33:44:55") {
                innerprinter_device = device
                return true
            }
        }
        return false
    }



    fun getToken(sharedPref: SharedPref): String {

        val lToken = sharedPref.getSharedToken() ?: 1

        sharedPref.setSharedToken(lToken)

        return if (lToken in 1..9){
            "0$lToken"
        } else {
            lToken.toString()
        }
    }



}
