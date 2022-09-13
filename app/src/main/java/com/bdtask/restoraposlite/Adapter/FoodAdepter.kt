package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Interface.FoodClickListener
import com.bdtask.restoraposlite.Room.Entity.Food
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.VhFoodItemBinding

class FoodAdepter(private val context: Context,
                  private var foodList: List<Food>,
                  private var foodClickListener: FoodClickListener) : RecyclerView.Adapter<FoodAdepter.FoodVHH>() {

    inner class FoodVHH(binding: VhFoodItemBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVHH {
        return FoodVHH(VhFoodItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_food_item, parent, false)))
    }

    override fun onBindViewHolder(holder: FoodVHH, position: Int) {

        holder.binding.foodImage.setImageURI(foodList[position].fImg.toUri())

        holder.binding.foodTile.text = foodList[position].fTitle

        if (foodList[position].fVar.isNotEmpty()) {
            holder.binding.foodVariant.text = foodList[position].fVar[0].vari
            holder.binding.foodPrice.text = foodList[position].fVar[0].fPrc.toString()
        }

        holder.itemView.setOnClickListener {

            foodClickListener.onFoodClick( foodList[position].id, foodList[position].fTitle, foodList[position].fVar, foodList[position].fAdns )

        }

        holder.itemView.setOnLongClickListener{

            foodClickListener.onFoodLongClick(foodList[position],foodClickListener)

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}