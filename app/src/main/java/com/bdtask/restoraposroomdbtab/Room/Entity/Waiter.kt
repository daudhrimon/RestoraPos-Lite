package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waiter_tbl")
data class Waiter(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val wNm: String
)
