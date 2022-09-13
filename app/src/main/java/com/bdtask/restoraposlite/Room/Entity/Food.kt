package com.bdtask.restoraposlite.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.Model.Adn
import com.bdtask.restoraposlite.Model.Variant

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
