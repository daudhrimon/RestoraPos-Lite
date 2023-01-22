package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waiter_tbl")
data class Waiter(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String
)
