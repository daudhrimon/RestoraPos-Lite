package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.FoodClickListener
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.databinding.VhFoodItemBinding

class FoodAdepter(private val context: Context,
                  private var foodList: MutableList<Food>,
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

        holder.binding.foodVariant.text = foodList[position].fVar[0].vari

        holder.binding.foodPrice.text = foodList[position].fVar[0].fPrc.toString()

        holder.itemView.setOnClickListener {
            foodClickListener.onFoodClick( foodList[position].id, foodList[position].fTitle, foodList[position].fVar, foodList[position].fAdns )
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}