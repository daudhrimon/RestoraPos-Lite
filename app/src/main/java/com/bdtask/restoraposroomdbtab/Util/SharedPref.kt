package com.bdtask.restoraposroomdbtab.Util

import android.content.Context
import android.content.SharedPreferences
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.OdrInf
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

    fun readSharedOrderInfoList(): OdrInf? {
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
}