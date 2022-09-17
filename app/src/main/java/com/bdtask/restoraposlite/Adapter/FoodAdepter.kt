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
import com.bumptech.glide.Glide

class FoodAdepter(private val context: Context,
                  private var foodList: List<Food>,
                  private var foodClickListener: FoodClickListener
                  ) : RecyclerView.Adapter<FoodAdepter.FoodVHH>() {

    inner class FoodVHH(val binding: VhFoodItemBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodVHH {
        return FoodVHH(VhFoodItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_food_item, parent, false)))
    }

    override fun onBindViewHolder(holder: FoodVHH, position: Int) {

        Glide.with(context).load(foodList[position].fImg)
            .placeholder(R.drawable.restora_logo).into(holder.binding.foodImage)

        if (foodList[position].fImg.isEmpty()){
            holder.binding.foodImage.imageAlpha = 123
        }

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