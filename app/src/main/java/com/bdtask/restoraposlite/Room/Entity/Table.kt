package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_tbl")
data class Table(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String
)
