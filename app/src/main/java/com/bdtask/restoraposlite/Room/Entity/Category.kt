package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_tbl")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val category: String
)
