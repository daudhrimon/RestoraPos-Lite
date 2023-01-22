package com.bdtask.restoraposlite.models

data class Payments(
    var typ: Int,
    var pay: String,
    var ter: String,
    var bnk: String,
    var amo: Double
)
