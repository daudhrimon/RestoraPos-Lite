package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.Model.OdrInf
import com.bdtask.restoraposroomdbtab.Model.Pay

@Entity(tableName = "merge_tbl")
data class Mrg (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val refs: MutableList<Long>,
    val sts: Int,
    val dat: String,
    val tkn: String,
    val dis: Double,
    val vat: Double,
    val crg: Double,
    val tPrc: Double,
    val odrInf: OdrInf,
    val cart: MutableList<Cart>,
    val pay: MutableList<Pay>
        )