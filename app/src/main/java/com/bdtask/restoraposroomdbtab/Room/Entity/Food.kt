package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.Model.Variant

@Entity(tableName = "food_tbl")
data class Food(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var fCategory: String,
    var fTitle: String,
    var fVariant: List<Variant>,
    var fImage: String,
    var fAddons: List<Addon>
)
