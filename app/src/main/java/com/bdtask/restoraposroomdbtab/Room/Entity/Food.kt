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
    var fTitle: String,
    var fVar: MutableList<Variant>,
    var fImg: String,
    var fAdns: MutableList<Adn>
)
