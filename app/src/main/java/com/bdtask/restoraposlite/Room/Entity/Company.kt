package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class Company(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String
)
