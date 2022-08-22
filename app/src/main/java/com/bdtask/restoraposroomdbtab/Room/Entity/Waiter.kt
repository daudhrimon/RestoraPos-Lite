package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waiter_tbl")
data class Waiter(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var wName: String
)
