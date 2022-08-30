package com.bdtask.restoraposroomdbtab.Model

data class OrderInfo(
    val customerInfo: CustomerInfo,
    val customerType: String,
    val waiter: String,
    val table: String,
    val deliveryCompany: String,
    val orderIdTp: String
)
