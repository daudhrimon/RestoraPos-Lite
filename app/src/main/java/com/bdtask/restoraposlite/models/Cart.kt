package com.bdtask.restoraposlite.models

data class Cart (
    var title: String,
    var variant: String,
    var varPrice: Double,
    var quantity: Int,
    var price: Double,
    var subTotal: Double,
    var addonTotal: Double,
    var addon: MutableList<Addon>,
    var note: String
)
