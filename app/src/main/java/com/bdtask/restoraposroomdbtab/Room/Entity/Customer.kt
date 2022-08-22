package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_tbl")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var email: String,
    var mobile: String,
    var address: String,
    var favAddress: String
    )