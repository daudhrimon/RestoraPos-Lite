package com.bdtask.restoraposroomdbtab.Interface

import com.bdtask.restoraposroomdbtab.Model.Adn
import com.bdtask.restoraposroomdbtab.Model.Variant

interface FoodClickListener {
    fun onFoodClick(foodId : Long?, foodTitle :String?, variantlist: List<Variant>, adnList: List<Adn>)
}