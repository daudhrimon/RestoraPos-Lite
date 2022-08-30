package com.bdtask.restoraposroomdbtab.Model

data class FoodCart (
    val foodTitle: String,
    val foodVariant: String,
    val variantPrice: Double,
    val foodQuantity: Int,
    val foodPrice: Double,
    val totalUnitPrice: Double,
    val addonsPrice: Double,
    val addonList: List<HomeAddon>,
    val note: String
)
