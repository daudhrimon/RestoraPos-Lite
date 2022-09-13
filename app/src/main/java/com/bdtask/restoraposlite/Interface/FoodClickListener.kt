package com.bdtask.restoraposlite.Interface

import com.bdtask.restoraposlite.Model.Adn
import com.bdtask.restoraposlite.Model.Variant
import com.bdtask.restoraposlite.Room.Entity.Food

interface FoodClickListener {
    fun onFoodClick(foodId : Long?, foodTitle :String?, variantList: List<Variant>, adnList: List<Adn>)

    fun onFoodLongClick(food: Food)
}