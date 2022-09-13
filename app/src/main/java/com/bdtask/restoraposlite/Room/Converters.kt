package com.bdtask.restoraposlite.Room

import androidx.room.TypeConverter
import com.bdtask.restoraposlite.Model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun addonListToJson(list: MutableList<Adn>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToAddonList(json: String): MutableList<Adn>?{
        val type = object : TypeToken<MutableList<Adn>>(){}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun variantListToJson(list: MutableList<Variant>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToVariantList(json: String): MutableList<Variant>?{
        val type = object : TypeToken<MutableList<Variant?>?>(){}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun foodCartListToJson(list: MutableList<Cart>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToFoodCartList(json: String): MutableList<Cart>?{
        val type = object : TypeToken<MutableList<Cart?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun payListToJson(list: MutableList<Pay>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToPayList(json: String): MutableList<Pay>?{
        val type = object : TypeToken<MutableList<Pay?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun orderInfoToJson(model: OdrInf): String?{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToOrderInfo(json: String): OdrInf?{
        val type = object : TypeToken<OdrInf?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun customerInfoToJson(model: CsInf): String?{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun jsonToCustomerInfo(json: String): CsInf?{
        val type = object : TypeToken<CsInf?>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun mrgRefListToJson(list: MutableList<Long>): String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToMrgRefList(json: String): MutableList<Long>?{
        val type = object : TypeToken<CsInf?>() {}.type
        return Gson().fromJson(json, type)
    }
}