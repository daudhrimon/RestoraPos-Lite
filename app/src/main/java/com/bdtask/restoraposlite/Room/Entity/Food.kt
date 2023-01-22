package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.models.Addons
import com.bdtask.restoraposlite.models.Variants

@Entity(tableName = "food_tbl")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var category: String,
    var title: String,
    var variants: MutableList<Variants>,
    var image: String,
    var addons: MutableList<Addons>
)
