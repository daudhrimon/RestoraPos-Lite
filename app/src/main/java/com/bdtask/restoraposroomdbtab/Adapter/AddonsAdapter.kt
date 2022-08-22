package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.Model.HomeAddon
import com.bdtask.restoraposroomdbtab.databinding.DialogFoodClickedBinding
import com.bdtask.restoraposroomdbtab.databinding.VhAddonsBinding

class AddonsAdapter(private val context: Context,
                    private var addonsList: List<Addon>,
                    private val foodDialogBinding: DialogFoodClickedBinding,
                    private var homeAddonList: MutableList<HomeAddon>): RecyclerView.Adapter<AddonsAdapter.VHFoodAddons>() {

    inner class VHFoodAddons(binding: VhAddonsBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHFoodAddons {
        return VHFoodAddons(VhAddonsBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_addons,parent,false)))
    }

    override fun onBindViewHolder(holder: VHFoodAddons, position: Int) {

        holder.binding.addonName.text = addonsList[position].adnName
        holder.binding.addonQuantity.text = "1"
        holder.binding.addonPrice.text = addonsList[position].adnPrice.toString()

        holder.binding.checkedBox.visibility = View.GONE
        holder.binding.unCheckedBox.visibility = View.VISIBLE

        holder.binding.addonPlusBtn.setOnClickListener {
            var addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
            var addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

            if (addonQuantity < 99) {

                addonQuantity += 1
                holder.binding.addonQuantity.text = addonQuantity.toString()

                addonnPrice += addonsList[position].adnPrice
                holder.binding.addonPrice.text = addonnPrice.toString()

                if (holder.binding.checkedBox.isVisible) {

                    var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()

                    foodPrice += addonsList[position].adnPrice
                    foodDialogBinding.dcFoodPrice.text = foodPrice.toString()

                    // adding and updating Addons Info at homeAddonsList
                    for (i in homeAddonList.indices) {
                        if (homeAddonList[i].adnName == addonsList[position].adnName) {
                            homeAddonList.removeAt(i)
                            homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                    return@setOnClickListener
                }
            }
        }

        holder.binding.addonMinusBtn.setOnClickListener {
            var addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
            var addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

            if (addonQuantity > 1) {

                addonQuantity -= 1
                holder.binding.addonQuantity.text = addonQuantity.toString()

                addonnPrice -= addonsList[position].adnPrice
                holder.binding.addonPrice.text = addonnPrice.toString()

                if (holder.binding.checkedBox.isVisible) {

                    var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()

                    foodPrice -= addonsList[position].adnPrice
                    foodDialogBinding.dcFoodPrice.text = foodPrice.toString()

                    // adding and updating Addons Info at homeAddonsList
                    for (i in homeAddonList.indices) {
                        if (homeAddonList[i].adnName == addonsList[position].adnName) {
                            homeAddonList.removeAt(i)
                            homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                    return@setOnClickListener
                }
            }
        }

        holder.binding.checkBox.setOnClickListener {
            if (holder.binding.unCheckedBox.isVisible) {
                // checkBox State is Checked

                holder.binding.unCheckedBox.visibility = View.GONE
                holder.binding.checkedBox.visibility = View.VISIBLE

                val addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
                val addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

                var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
                foodPrice += addonnPrice
                foodDialogBinding.dcFoodPrice.text = foodPrice.toString()

                // adding and updating Addons Info at homeAddonsList
                if (homeAddonList.size == 0) {
                    homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                } else {
                    for (i in homeAddonList.indices) {
                        if (homeAddonList[i].adnName == addonsList[position].adnName) {
                            homeAddonList.removeAt(i)
                            homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    homeAddonList.add(HomeAddon(addonsList[position].adnName, addonQuantity, addonnPrice))
                    return@setOnClickListener
                }

            } else {
                // checkBox State is UnChecked

                holder.binding.checkedBox.visibility = View.GONE
                holder.binding.unCheckedBox.visibility = View.VISIBLE

                //var addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
                val addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

                var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
                foodPrice -= addonnPrice
                foodDialogBinding.dcFoodPrice.text = foodPrice.toString()

                // adding and updating Addons Info at homeAddonsList
                if (homeAddonList.size > 0) {
                    for (i in homeAddonList.indices) {
                        if (homeAddonList[i].adnName == addonsList[position].adnName) {
                            homeAddonList.removeAt(i)
                            return@setOnClickListener
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return addonsList.size
    }
}