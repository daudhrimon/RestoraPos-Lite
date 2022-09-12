package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.FoodClickListener
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.foodList
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding

class CategoryAdapter( private val context: Context,
                       private var cateList: MutableList<String>,
                       private val searchEt: EditText,
                       private var foodClickListener: FoodClickListener
                       ): RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private val tempFoodList = mutableListOf<Food>()

    inner class CategoryVH(binding: VhFoodRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(VhFoodRecyclerBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_food_recycler,parent,false)))
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {

        if (position == 0){
            if (foodList.isNotEmpty()){
                holder.binding.emptyBox.visibility = View.GONE
                holder.binding.foodRecycler.visibility = View.VISIBLE
                holder.binding.foodRecycler.adapter = FoodAdepter(context,foodList,searchEt,foodClickListener)
            }else{
                holder.binding.foodRecycler.visibility = View.GONE
                holder.binding.emptyBox.visibility = View.VISIBLE
            }
        } else {
            tempFoodList.clear()
            for (i in foodList.indices){
                if (foodList[i].fCate == cateList[position]){
                    tempFoodList.add(foodList[i])
                }
            }
            if(tempFoodList.isNotEmpty()){
                holder.binding.emptyBox.visibility = View.GONE
                holder.binding.foodRecycler.visibility = View.VISIBLE
                holder.binding.foodRecycler.adapter = FoodAdepter(context,tempFoodList,searchEt,foodClickListener)
            } else {
                holder.binding.foodRecycler.visibility = View.GONE
                holder.binding.emptyBox.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
      return cateList.size
    }
}