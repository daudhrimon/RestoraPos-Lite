package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_tbl")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fCategory : String
)
