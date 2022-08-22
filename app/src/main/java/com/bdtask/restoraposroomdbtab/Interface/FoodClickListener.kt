package com.bdtask.restoraposroomdbtab.Interface

import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.Model.Variant

interface FoodClickListener {
    fun onFoodClick(foodId : Long?, foodTitle :String?, variantlist: List<Variant>, addonList: List<Addon>)
}