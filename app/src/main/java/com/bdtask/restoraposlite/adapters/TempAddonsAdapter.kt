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
import com.bdtask.restoraposlite.models.Addons
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogInsertAddonBinding
import com.bdtask.restoraposlite.databinding.VhTempVarAddonBinding
import es.dmoral.toasty.Toasty

class TempAddonsAdapter(
    private val context: Context,
    private var list: MutableList<Addons>
) : RecyclerView.Adapter<TempAddonsAdapter.AddonsVh>() {

    inner class AddonsVh(val binding: VhTempVarAddonBinding) :
        RecyclerView.ViewHolder(binding.root) {/**/ }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonsVh {
        return AddonsVh(
            VhTempVarAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.vh_temp_var_addon, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: AddonsVh, position: Int) {

        holder.binding.itemNameTv.text = "Addon : " + list[position]?.name
        holder.binding.itemPriceTv.text = "Price : " + list[position]?.price.toString()

        holder.binding.itemEditBtn.setOnClickListener {
            val dialog = Dialog(context)
            val binding = DialogInsertAddonBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon, null)
            )
            dialog.setContentView(binding.root)

            binding.addonHeaderTv.text = "Update Addon"
            binding.addonNameEt.setText(list[position]?.name)
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
                    binding.addonPriceEt.setText("0.0")
                }

                if (binding.addonNameEt.text.toString() == list[position]?.name &&
                    binding.addonPriceEt.text.toString().toDouble() == list[position]?.price
                ) {
                    Toasty.error(context, "Same as Old", Toast.LENGTH_SHORT, true).show()
                    return@setOnClickListener
                }

                for (i in list.indices) {
                    if (list[i]?.name == binding.addonNameEt.text.toString() && list[i]?.name != list[position]?.name) {
                        Toasty.error(context, "Already Available", Toast.LENGTH_SHORT, true).show()
                        return@setOnClickListener
                    }
                }

                list[position] = Addons(
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