package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Model.Adn
import com.bdtask.restoraposlite.Model.Adns
import com.bdtask.restoraposlite.databinding.DialogFoodClickBinding
import com.bdtask.restoraposlite.databinding.VhAddonsBinding

class AddonsAdapter(private val context: Context,
                    private var addonsList: List<Adn>,
                    private val fcBinding: DialogFoodClickBinding,
                    private var adnsList: MutableList<Adns>): RecyclerView.Adapter<AddonsAdapter.VHFoodAddons>() {

    inner class VHFoodAddons(val binding: VhAddonsBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHFoodAddons {
        return VHFoodAddons(VhAddonsBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_addons,parent,false)))
    }

    override fun onBindViewHolder(holder: VHFoodAddons, position: Int) {

        holder.binding.addonName.text = addonsList[position].adnNm
        holder.binding.addonQuantity.text = "1"
        holder.binding.addonPrice.text = addonsList[position].adnPrc.toString()

        holder.binding.checkedBox.visibility = View.GONE
        holder.binding.unCheckedBox.visibility = View.VISIBLE

        holder.binding.addonPlusBtn.setOnClickListener {
            var addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
            var addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

            if (addonQuantity < 99) {

                addonQuantity += 1
                holder.binding.addonQuantity.text = addonQuantity.toString()

                addonnPrice += addonsList[position].adnPrc
                holder.binding.addonPrice.text = addonnPrice.toString()

                if (holder.binding.checkedBox.isVisible) {

                    var foodPrice = fcBinding.dcFoodPrice.text.toString().toDouble()

                    foodPrice += addonsList[position].adnPrc
                    fcBinding.dcFoodPrice.text = foodPrice.toString()

                    // adding and updating Addons Info at homeAddonsList
                    for (i in adnsList.indices) {
                        if (adnsList[i].adnNm == addonsList[position].adnNm) {
                            adnsList.removeAt(i)
                            adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
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

                addonnPrice -= addonsList[position].adnPrc
                holder.binding.addonPrice.text = addonnPrice.toString()

                if (holder.binding.checkedBox.isVisible) {

                    var foodPrice = fcBinding.dcFoodPrice.text.toString().toDouble()

                    foodPrice -= addonsList[position].adnPrc
                    fcBinding.dcFoodPrice.text = foodPrice.toString()

                    // adding and updating Addons Info at homeAddonsList
                    for (i in adnsList.indices) {
                        if (adnsList[i].adnNm == addonsList[position].adnNm) {
                            adnsList.removeAt(i)
                            adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
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

                var foodPrice = fcBinding.dcFoodPrice.text.toString().toDouble()
                foodPrice += addonnPrice
                fcBinding.dcFoodPrice.text = foodPrice.toString()

                // adding and updating Addons Info at homeAddonsList
                if (adnsList.size == 0) {
                    adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
                } else {
                    for (i in adnsList.indices) {
                        if (adnsList[i].adnNm == addonsList[position].adnNm) {
                            adnsList.removeAt(i)
                            adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
                            return@setOnClickListener
                        }
                    }
                    adnsList.add(Adns(addonsList[position].adnNm, addonQuantity, addonnPrice))
                    return@setOnClickListener
                }

            } else {
                // checkBox State is UnChecked

                holder.binding.checkedBox.visibility = View.GONE
                holder.binding.unCheckedBox.visibility = View.VISIBLE

                //var addonQuantity = holder.binding.addonQuantity.text.toString().toInt()
                val addonnPrice = holder.binding.addonPrice.text.toString().toDouble()

                var foodPrice = fcBinding.dcFoodPrice.text.toString().toDouble()
                foodPrice -= addonnPrice
                fcBinding.dcFoodPrice.text = foodPrice.toString()

                // adding and updating Addons Info at homeAddonsList
                if (adnsList.size > 0) {
                    for (i in adnsList.indices) {
                        if (adnsList[i].adnNm == addonsList[position].adnNm) {
                            adnsList.removeAt(i)
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