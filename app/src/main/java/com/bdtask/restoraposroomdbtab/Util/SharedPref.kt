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

    fun writeSharedSplit(order: Order) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("split", Gson().toJson(order))
        prefsEditor.apply()
    }

    fun readSharedSplit() : Order? {
        val type = object : TypeToken<Order?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("split", ""), type)
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
}