package com.bdtask.restoraposroomdbtab.Model

data class FoodCart (
    var foodTitle: String,
    var foodVariant: String,
    var variantPrice: Double,
    var foodQuantity: Int,
    var foodPrice: Double,
    var totalUnitPrice: Double,
    var addonsPrice: Double,
    var addonList: List<HomeAddon>,
    var note: String
)
