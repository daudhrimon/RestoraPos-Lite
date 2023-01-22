package com.bdtask.restoraposlite.models

data class OrderInfo(
    val customerInfo: CustomerInfo,
    val csTyp: String,
    val wtr: String,
    val tbl: String,
    val dlvCo: String,
    val odrIdTp: String
)
