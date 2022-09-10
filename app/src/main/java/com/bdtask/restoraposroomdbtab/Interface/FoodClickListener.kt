package com.bdtask.restoraposroomdbtab.Interface

import com.bdtask.restoraposroomdbtab.Model.Adn
import com.bdtask.restoraposroomdbtab.Model.Variant
import com.bdtask.restoraposroomdbtab.Room.Entity.Food

interface FoodClickListener {
    fun onFoodClick(foodId : Long?, foodTitle :String?, variantList: List<Variant>, adnList: List<Adn>)

    fun onFoodLongClick(food: Food)
}