package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.Model.Cart

@Entity(tableName = "split_tbl")
data class Split(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val ref: Long,
    val sts: Int,
    var csInf: CsInf,
    val cart: MutableList<Cart>
)
