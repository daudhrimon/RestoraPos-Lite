package com.bdtask.restoraposroomdbtab.Room

import androidx.room.TypeConverter
import com.bdtask.restoraposroomdbtab.Model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun addonListToJson(list: List<Addon>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToAddonList(json: String): List<Addon>?{
        val type = object : TypeToken<List<Addon>>(){}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun variantListToJson(list: List<Variant>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToVariantList(json: String): List<Variant>?{
        val type = object : TypeToken<List<Variant?>?>(){}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun foodCartListToJson(list: List<FoodCart>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToFoodCartList(json: String): List<FoodCart>?{
        val type = object : TypeToken<List<FoodCart?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun orderInfoToJson(model: OrderInfo): String?{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToOrderInfo(json: String): OrderInfo?{
        val type = object : TypeToken<OrderInfo?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun customerInfoToJson(model: CustomerInfo): String?{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToCustomerInfo(json: String): CustomerInfo?{
        val type = object : TypeToken<CustomerInfo?>() {}.type
        return Gson().fromJson(json, type)
    }
}