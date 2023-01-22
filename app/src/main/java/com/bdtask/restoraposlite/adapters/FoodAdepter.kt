package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.interfaces.FoodClickListener
import com.bdtask.restoraposlite.room.entity.Food
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhFoodItemBinding
import com.bumptech.glide.Glide

class FoodAdepter(
    private val context: Context,
    private var foodList: List<Food>,
    private var foodClickListener: FoodClickListener
) : RecyclerView.Adapter<FoodAdepter.FoodVHH>() {

    inner class FoodVHH(val binding: VhFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVHH {
        return FoodVHH(
            VhFoodItemBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_food_item, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: FoodVHH, position: Int) {

        Glide.with(context).load(foodList[position].image)
            .placeholder(R.drawable.restora_logo).into(holder.binding.foodImage)

        holder.binding.foodTile.text = foodList[position].title

        if (foodList[position].variants.isNotEmpty()) {
            holder.binding.foodVariant.text = foodList[position].variants[0].variant
            holder.binding.foodPrice.text = foodList[position].variants[0].price.toString()
        }

        holder.itemView.setOnClickListener {

            foodClickListener.onFoodClick(
                foodList[position].id,
                foodList[position].title,
                foodList[position].variants,
                foodList[position].addons
            )
        }

        holder.itemView.setOnLongClickListener {

            foodClickListener.onFoodLongClick(foodList[position], foodClickListener)

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}