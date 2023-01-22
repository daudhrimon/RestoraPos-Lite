package com.bdtask.restoraposlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.databinding.OnboardLayoutBinding

class OnboardAdapter(
    private var context: Context,
    private val images: IntArray,
    private val texts: IntArray
) : RecyclerView.Adapter<OnboardAdapter.OnBoardingVH>() {

    inner class OnBoardingVH(val binding: OnboardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingVH {
        return OnBoardingVH(
            OnboardLayoutBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.onboard_layout, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingVH, position: Int) {
        holder.binding.onbImage.setImageResource(images[position])
        holder.binding.onbText.text = context.getString(texts[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}