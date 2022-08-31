package com.bdtask.restoraposroomdbtab.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Variant
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogInsertAddonBinding
import com.bdtask.restoraposroomdbtab.databinding.VhTempVarAddonBinding
import es.dmoral.toasty.Toasty

class TempVariantAdapter(private val context: Context,
                         private var list: MutableList<Variant>): RecyclerView.Adapter<TempVariantAdapter.VHtempVA>() {

    inner class VHtempVA(binding: VhTempVarAddonBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHtempVA {
        return VHtempVA(VhTempVarAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_temp_var_addon,parent,false)))
    }

    override fun onBindViewHolder(holder: VHtempVA, position: Int) {
        holder.binding.itemNameTv.text = "Variant : " + list[position]?.vari
        holder.binding.itemPriceTv.text = "Price : " + list[position]?.fPrc.toString()

        holder.binding.itemEditBtn.setOnClickListener {
            val dialog = Dialog(context)
            val binding = DialogInsertAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon, null))
            dialog.setContentView(binding.root)

            binding.addonHeaderTv.text = "Update Variant"
            binding.addonNameEt.hint = "Enter Variant"
            binding.addonNameEt.setText(list[position]?.vari)
            binding.addonPriceEt.hint = "Enter Price"
            binding.addonPriceEt.setText(list[position]?.fPrc.toString())
            binding.addonAddBtn.text = "Update"

            binding.root.setOnClickListener { Util.hideSoftKeyBoard(context, binding.root) }

            binding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

            binding.addonAddBtn.setOnClickListener {
                if (binding.addonNameEt.text.toString().isEmpty()) {
                    binding.addonNameEt.setError("Empty Value Forbidden")
                    binding.addonNameEt.requestFocus()
                    return@setOnClickListener
                }

                if (binding.addonPriceEt.text.toString().isEmpty()){
                    binding.addonPriceEt.setError("Empty Value Forbidden")
                    binding.addonNameEt.requestFocus()
                    return@setOnClickListener
                }

                if (binding.addonNameEt.text.toString() == list[position]?.vari &&
                    binding.addonPriceEt.text.toString().toDouble() == list[position]?.fPrc) {
                    Toasty.error(context,"Same as Old",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }

                for (i in list.indices) {
                    if (list[i]?.vari == binding.addonNameEt.text.toString() && list[i]?.vari != list[position]?.vari) {
                        Toasty.error(context,"Already Available",Toast.LENGTH_SHORT,true).show()
                        return@setOnClickListener
                    }
                }

                list[position] = Variant(binding.addonNameEt.text.toString(),binding.addonPriceEt.text.toString().toDouble())
                Toasty.success(context,"Successful",Toast.LENGTH_SHORT,true).show()
                notifyDataSetChanged()
                dialog.dismiss()
            }
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
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