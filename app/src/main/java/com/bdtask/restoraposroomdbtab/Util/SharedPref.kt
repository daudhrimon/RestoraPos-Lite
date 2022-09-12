package com.bdtask.restoraposroomdbtab.Util

import android.content.Context
import android.content.SharedPreferences
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.OdrInf
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.Room.Entity.Cstmr
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPref {
    ///val SHARED_PREF_MAIN = "shared_preference_main"
    private var mSharedPref: SharedPreferences? = null
    val NAME = "com.bdtask.pos"

    private fun SharedPref() {}

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
                context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun writeEMode(eMode: Int){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt("eMode",eMode)
        prefsEditor.apply()
    }

    fun readEMOde(): Int? {
        return mSharedPref!!.getInt("eMode", 0)
    }

    fun writeCart(cartList: MutableList<Cart>) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("carts", Gson().toJson(cartList))
        prefsEditor.apply()
    }

    fun readCart() : MutableList<Cart>? {
        val type = object : TypeToken<MutableList<Cart?>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("carts", emptyList<Cart>().toString()), type)
    }

    fun writeOrderInfo(odrInf: OdrInf) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("orderInf", Gson().toJson(odrInf))
        prefsEditor.apply()
    }

    fun readOrderInfo(): OdrInf? {
        val type = object : TypeToken<OdrInf?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("orderInf", ""), type)
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

    fun writeOrder(order: Order) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("order", Gson().toJson(order))
        prefsEditor.apply()
    }

    fun readOrder() : Order? {
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
        prefsEditor.putString("term", Gson().toJson(bank))
        prefsEditor.apply()
    }

    fun readTerminalList(): MutableList<String>? {
        val type = object : TypeToken<MutableList<String>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("term", ""), type)
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
        return mSharedPref!!.getString("curr", "$") ?: "$"
    }

    fun writeOperator(ope: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("ope",ope)
        prefsEditor.apply()
    }

    fun readOperator(): String? {
        return mSharedPref!!.getString("ope", "") ?: ""
    }

    fun writeResInf(info: Cstmr) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("inf", Gson().toJson(info))
        prefsEditor.apply()
    }

    fun readResInf() : Cstmr? {
        val type = object : TypeToken<Cstmr?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("inf", ""), type)
    }

    fun writePosLogo(logo: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("logo",logo)
        prefsEditor.apply()
    }

    fun readPosLogo(): String? {
        return mSharedPref!!.getString("logo", "") ?: ""
    }
}