package com.bdtask.restoraposlite.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.models.Variants
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogInsertAddonBinding
import com.bdtask.restoraposlite.databinding.VhTempVarAddonBinding
import es.dmoral.toasty.Toasty

class TempVariantAdapter(
    private val context: Context,
    private var list: MutableList<Variants>
) : RecyclerView.Adapter<TempVariantAdapter.VhTempVA>() {

    inner class VhTempVA(var binding: VhTempVarAddonBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhTempVA {
        return VhTempVA(
            VhTempVarAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_temp_var_addon, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: VhTempVA, position: Int) {
        holder.binding.itemNameTv.text = "Variant : " + list[position]?.variant
        holder.binding.itemPriceTv.text = "Price : " + list[position]?.price.toString()

        holder.binding.itemEditBtn.setOnClickListener {
            val dialog = Dialog(context)
            val binding = DialogInsertAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon, null)
            )
            dialog.setContentView(binding.root)

            binding.addonHeaderTv.text = "Update Variant"
            binding.addonNameEt.hint = "Enter Variant"
            binding.addonNameEt.setText(list[position]?.variant)
            binding.addonPriceEt.hint = "Enter Price"
            binding.addonPriceEt.setText(list[position]?.price.toString())
            binding.addonAddBtn.text = "Update"

            binding.root.setOnClickListener { Util.hideSoftKeyBoard(context, binding.root) }

            binding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

            binding.addonAddBtn.setOnClickListener {
                if (binding.addonNameEt.text.toString().isEmpty()) {
                    binding.addonNameEt.setError("Empty Value Forbidden")
                    binding.addonNameEt.requestFocus()
                    return@setOnClickListener
                }

                if (binding.addonPriceEt.text.toString().isEmpty()) {
                    binding.addonPriceEt.setError("Empty Value Forbidden")
                    binding.addonNameEt.requestFocus()
                    return@setOnClickListener
                }

                if (binding.addonNameEt.text.toString() == list[position]?.variant &&
                    binding.addonPriceEt.text.toString().toDouble() == list[position]?.price
                ) {
                    Toasty.error(context, "Same as Old", Toast.LENGTH_SHORT, true).show()
                    return@setOnClickListener
                }

                for (i in list.indices) {
                    if (list[i]?.variant == binding.addonNameEt.text.toString() && list[i]?.variant != list[position]?.variant) {
                        Toasty.error(context, "Already Available", Toast.LENGTH_SHORT, true).show()
                        return@setOnClickListener
                    }
                }

                list[position] = Variants(
                    binding.addonNameEt.text.toString(),
                    binding.addonPriceEt.text.toString().toDouble()
                )
                Toasty.success(context, "Successful", Toast.LENGTH_SHORT, true).show()
                notifyDataSetChanged()
                dialog.dismiss()
            }
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        holder.binding.itemDltBtn.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}