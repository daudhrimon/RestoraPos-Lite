package com.bdtask.restoraposlite.room

import androidx.room.TypeConverter
import com.bdtask.restoraposlite.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun addonListToJson(list: MutableList<Addons>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToAddonList(json: String): MutableList<Addons>? {
        val type = object : TypeToken<MutableList<Addons>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun variantListToJson(list: MutableList<Variants>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToVariantList(json: String): MutableList<Variants>? {
        val type = object : TypeToken<MutableList<Variants?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun foodCartListToJson(list: MutableList<Cart>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToFoodCartList(json: String): MutableList<Cart>? {
        val type = object : TypeToken<MutableList<Cart?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun payListToJson(list: MutableList<Payments>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToPayList(json: String): MutableList<Payments>? {
        val type = object : TypeToken<MutableList<Payments?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun orderInfoToJson(model: OrderInfo): String? {
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToOrderInfo(json: String): OrderInfo? {
        val type = object : TypeToken<OrderInfo?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun customerInfoToJson(model: CustomerInfo): String? {
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToCustomerInfo(json: String): CustomerInfo? {
        val type = object : TypeToken<CustomerInfo?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun mrgRefListToJson(list: MutableList<Long>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToMrgRefList(json: String): MutableList<Long>? {
        val type = object : TypeToken<CustomerInfo?>() {}.type
        return Gson().fromJson(json, type)
    }
}