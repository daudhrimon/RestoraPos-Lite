package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_tbl")
data class Cstmr(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nm: String,
    val eml: String,
    val mbl: String,
    val adrs: String
    )