package com.bdtask.restoraposroomdbtab.Util

import android.content.Context
import android.content.SharedPreferences
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.Model.OrderInfo
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

    fun writeSharedCartList(cartList: List<FoodCart>) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("cartList", Gson().toJson(cartList))
        prefsEditor.apply()
    }

    fun readSharedCartList() : List<FoodCart>? {
        val type = object : TypeToken<List<FoodCart?>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("cartList", emptyList<FoodCart>().toString()), type)
    }

    fun writeSharedOrderInfoList(orderInfoList: List<OrderInfo>) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("orderInfoList", Gson().toJson(orderInfoList))
        prefsEditor.apply()
    }

    fun readSharedOrderInfoList() : List<OrderInfo>? {
        val type = object : TypeToken<List<OrderInfo?>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("orderInfoList", emptyList<OrderInfo>().toString()), type)
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

    /*fun writeSharedCustomerInfo(customerInfo: MutableList<CustomerInfo>) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("customerInfo", Gson().toJson(customerInfo))
        prefsEditor.apply()
    }

    fun readSharedCustomerInfo(): MutableList<CustomerInfo>?{
        val type = object : TypeToken<MutableList<CustomerInfo?>?>() {}.type
        return Gson().fromJson(mSharedPref!!.getString("customerInfo", ""), type)
    }

    fun writeSharedCustomerType(customerType: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("customerType", customerType)
        prefsEditor.apply()
    }

    fun readSharedCustomerType(): String?{
        return mSharedPref!!.getString("customerType", "")
    }

    fun writeSharedWaiter(waiter: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("waiter", waiter)
        prefsEditor.apply()
    }

    fun readSharedWaiter(): String?{
        return mSharedPref!!.getString("waiter", "")
    }

    fun writeSharedTable(table: String){
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("table", table)
        prefsEditor.apply()
    }

    fun readSharedTable(): String?{
        return mSharedPref!!.getString("table", "")
    }*/
}