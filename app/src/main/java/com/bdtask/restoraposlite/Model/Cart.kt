package com.bdtask.restoraposlite.Model

data class Cart (
    var title: String,
    var vari: String,
    var varPrc: Double,
    var fQnty: Int,
    var fPrc: Double,
    var tUPrc: Double,
    var adnPrc: Double,
    var adns: MutableList<Adns>,
    var nt: String
)
