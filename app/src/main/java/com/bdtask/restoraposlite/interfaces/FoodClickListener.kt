package com.bdtask.restoraposlite.interfaces

import com.bdtask.restoraposlite.models.Addons
import com.bdtask.restoraposlite.models.Variants
import com.bdtask.restoraposlite.room.entity.Food

interface FoodClickListener {

    fun onFoodClick(foodId : Long?, foodTitle :String?, variantsList: List<Variants>, addonsList: List<Addons>)

    fun onFoodLongClick(food: Food, foodClickListener: FoodClickListener)

}