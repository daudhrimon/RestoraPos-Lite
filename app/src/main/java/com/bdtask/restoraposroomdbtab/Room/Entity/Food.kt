package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.Adn
import com.bdtask.restoraposroomdbtab.Model.Variant

@Entity(tableName = "food_tbl")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var fCate: String,
    val fTitle: String,
    val fVar: MutableList<Variant>,
    val fImg: String,
    val fAdns: List<Adn>
)
