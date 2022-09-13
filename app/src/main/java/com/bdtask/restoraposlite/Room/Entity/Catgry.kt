package com.bdtask.restoraposlite.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_tbl")
data class Catgry(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fCat : String
)
