package com.bdtask.restoraposroomdbtab.Util

import android.content.Context
import android.content.SharedPreferences
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.OdrInf
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPref {
    ///val SHARED_PREF_MAIN = "shared_preference_main"
    private var mSharedPref: SharedPreferences? = null
    val NAME = "com.bdtask.POS"

    private fun SharedPref() {}

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
                context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun writeEMode(eMode: Int){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt("EMode",eMode)
        prefsEditor.apply()
    }

    fun readEMOde(): Int? {
        return mSharedPref!!.getInt("eMode", 0)
    }

    fun writeSharedCartList(cartList: MutableList<Cart>) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("cartList", Gson().toJson(cartList))
        prefsEditor.apply()
    }

    fun readSharedCartList() : MutableList<Cart>? {
        val type = object : TypeToken<MutableList<Cart?>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("cartList", emptyList<Cart>().toString()), type)
    }

    fun writeSharedOrderInfoList(odrInf: OdrInf) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("orderInfo", Gson().toJson(odrInf))
        prefsEditor.apply()
    }

    fun readSharedOrderInfo(): OdrInf? {
        val type = object : TypeToken<OdrInf?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("orderInfo", ""), type)
    }

    fun setSharedToken(token: Long) {
        val prefsEditor = mSharedPref!!.edit()
        val toKen = 1 + token
        prefsEditor.putLong("token",toKen)
        prefsEditor.apply()
    }

    fun getSharedToken(): Long?{
        return mSharedPref!!.getLong("token",1)
    }

    fun writeSharedOrder(order: Order) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("order", Gson().toJson(order))
        prefsEditor.apply()
    }

    fun readSharedOrder() : Order? {
        val type = object : TypeToken<Order?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("order", ""), type)
    }

    fun writeBankList(bank: MutableList<String>?){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("bank", Gson().toJson(bank))
        prefsEditor.apply()
    }

    fun readBankList(): MutableList<String>? {
        val type = object : TypeToken<MutableList<String>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("bank", ""), type)
    }

    fun writeTerminalList(bank: MutableList<String>?){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("terminal", Gson().toJson(bank))
        prefsEditor.apply()
    }

    fun readTerminalList(): MutableList<String>? {
        val type = object : TypeToken<MutableList<String>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("terminal", ""), type)
    }

    fun writePayList(bank: MutableList<String>?){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("pay", Gson().toJson(bank))
        prefsEditor.apply()
    }

    fun readPayList(): MutableList<String>? {
        val type = object : TypeToken<MutableList<String>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("pay", ""), type)
    }

    fun writeVat(vat: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("vat",vat)
        prefsEditor.apply()
    }

    fun readVat(): Double? {
        return mSharedPref!!.getString("vat", "0.0")?.toDouble()
    }

    fun writeCharge(crg: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("crg",crg)
        prefsEditor.apply()
    }

    fun readCharge(): Double? {
        return mSharedPref!!.getString("crg", "0.0")?.toDouble()
    }

    fun writeCurrency(curr: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("curr",curr)
        prefsEditor.apply()
    }

    fun readCurrency(): String? {
        return mSharedPref!!.getString("curr", "") ?: ""
    }
}