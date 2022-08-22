package com.bdtask.restoraposroomdbtab.Model

data class OrderInfo(
    var customerInfo: List<CustomerInfo>,
    var customerType: String,
    var waiter: String,
    var table: String,
    var deliveryCompany: String,
    var orderIdTp: String
)
