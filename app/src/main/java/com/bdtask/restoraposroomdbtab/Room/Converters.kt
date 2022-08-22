package com.bdtask.restoraposroomdbtab.Room

import androidx.room.TypeConverter
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.Model.OrderInfo
import com.bdtask.restoraposroomdbtab.Model.Variant
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
    fun orderInfoListToJson(list: List<OrderInfo>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToOrderInfoList(json: String): List<OrderInfo>?{
        val type = object : TypeToken<List<OrderInfo?>?>() {}.type
        return Gson().fromJson(json, type)
    }
}