package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.Model.Variant

@Entity(tableName = "food_tbl")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fCategory: String,
    val fTitle: String,
    val fVariant: List<Variant>,
    val fImage: String,
    val fAddons: List<Addon>
)
