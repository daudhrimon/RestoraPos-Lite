package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class Cmpny(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val cNm: String
)
