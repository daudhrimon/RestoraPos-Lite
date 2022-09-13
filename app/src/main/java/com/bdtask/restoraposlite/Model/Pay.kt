package com.bdtask.restoraposlite.Model

data class Pay(
    var typ: Int,
    val pay: String,
    var ter: String,
    var bnk: String,
    var amo: Double
)
