package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.FoodClickListener
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.foodList
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

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
        tempFoList = foodList

        if (position == 0){
            if (tempFoList.isNotEmpty()){
                holder.binding.emptyBox.visibility = View.GONE
                holder.binding.foodRecycler.visibility = View.VISIBLE
                holder.binding.foodRecycler.adapter = FoodAdepter(context,tempFoList,searchEt,foodClickListener)
            }else{
                holder.binding.foodRecycler.visibility = View.GONE
                holder.binding.emptyBox.visibility = View.VISIBLE
            }
        } else {
            tempFoList.toMutableList().clear()
            for (i in foodList.indices){
                if (foodList[i].fCate == cateList[position]){
                    tempFoList.toMutableList().add(foodList[i])
                }
            }
            if(tempFoList.isNotEmpty()){
                holder.binding.emptyBox.visibility = View.GONE
                holder.binding.foodRecycler.visibility = View.VISIBLE
                holder.binding.foodRecycler.adapter = FoodAdepter(context,tempFoList,searchEt,foodClickListener)
            } else {
                holder.binding.foodRecycler.visibility = View.GONE
                holder.binding.emptyBox.visibility = View.VISIBLE
            }
        }



        searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Food>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()
                Log.wtf("DDDDDDD",srsList.toString())
                for (i in tempFoList.indices){
                    if (tempFoList[i].fTitle.lowercase().contains(text.toString().lowercase())){
                        srsList.add(tempFoList[i])
                    }
                }
                holder.binding.foodRecycler.adapter = FoodAdepter(context,srsList,searchEt,foodClickListener)
            } else {
                holder.binding.foodRecycler.adapter = FoodAdepter(context,tempFoList,searchEt,foodClickListener)
            }
        }
    }

    override fun getItemCount(): Int {
      return cateList.size
    }
}