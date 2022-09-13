package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Interface.FoodClickListener
import com.bdtask.restoraposlite.MainActivity.Companion.foodList
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Food
import com.bdtask.restoraposlite.databinding.VhFoodRecyclerBinding

class CategoryAdapter( private val context: Context,
                       private var cateList: MutableList<String>,
                       private val searchEt: EditText,
                       private var foodClickListener: FoodClickListener): RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private var tempFoList = listOf<Food>()

    inner class CategoryVH(val binding: VhFoodRecyclerBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(VhFoodRecyclerBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_food_recycler,parent,false)))
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        tempFoList = foodList.toList()

        if (position == 0){

            setFoodAdapter(holder)

        } else {
            tempFoList.toMutableList().clear()
            for (i in foodList.indices){
                if (foodList[i].fCate == cateList[position]){
                    tempFoList.toMutableList().add(foodList[i])
                }
            }

            setFoodAdapter(holder)

        }



        searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Food>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()

                for (i in tempFoList.indices){
                    if (tempFoList[i].fTitle.lowercase().contains(text.toString().lowercase())){
                        srsList.add(tempFoList[i])
                    }
                }
                if (srsList.isNotEmpty()){
                    holder.binding.emptyBox.visibility = View.GONE
                    holder.binding.foodRecycler.visibility = View.VISIBLE
                    holder.itemView.setBackgroundColor(getColor(context,R.color.disableColor))
                    holder.binding.foodRecycler.adapter = FoodAdepter(context,srsList,foodClickListener)
                } else {
                    holder.binding.foodRecycler.visibility = View.GONE
                    holder.binding.emptyBox.visibility = View.VISIBLE
                    holder.itemView.setBackgroundColor(getColor(context,R.color.disableColor))
                }
            } else {
                holder.itemView.setBackgroundColor(getColor(context,R.color.white))

                setFoodAdapter(holder)
            }
        }
    }

    private fun setFoodAdapter(holder: CategoryVH) {
        if (tempFoList.isNotEmpty()){
            holder.binding.emptyBox.visibility = View.GONE
            holder.binding.foodRecycler.visibility = View.VISIBLE
            holder.binding.foodRecycler.adapter = FoodAdepter(context,tempFoList,foodClickListener)
        }else{
            holder.binding.foodRecycler.visibility = View.GONE
            holder.binding.emptyBox.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
      return cateList.size
    }
}