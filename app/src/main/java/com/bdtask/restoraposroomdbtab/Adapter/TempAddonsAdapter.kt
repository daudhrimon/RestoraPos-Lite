package com.bdtask.restoraposroomdbtab.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogInsertAddonBinding
import com.bdtask.restoraposroomdbtab.databinding.VhTempVarAddonBinding
import es.dmoral.toasty.Toasty

class TempAddonsAdapter(private val context: Context,
                        private var list: MutableList<Addon>): RecyclerView.Adapter<TempAddonsAdapter.AddonsVh>() {

    inner class AddonsVh(binding: VhTempVarAddonBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonsVh {
        return AddonsVh(VhTempVarAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_temp_var_addon,parent,false)))
    }

    override fun onBindViewHolder(holder: AddonsVh, position: Int) {

        holder.binding.itemNameTv.text = "Addon : " + list[position]?.adnName
        holder.binding.itemPriceTv.text = "Price : " + list[position]?.adnPrice.toString()

        holder.binding.itemEditBtn.setOnClickListener {
            val dialog = Dialog(context)
            val binding = DialogInsertAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon, null))
            dialog.setContentView(binding.root)

            binding.addonHeaderTv.text = "Update Addon"
            binding.addonNameEt.setText(list[position]?.adnName)
            binding.addonPriceEt.setText(list[position]?.adnPrice.toString())
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
                    binding.addonPriceEt.setText("0.0")
                }

                if (binding.addonNameEt.text.toString() == list[position]?.adnName &&
                    binding.addonPriceEt.text.toString().toDouble() == list[position]?.adnPrice) {
                    Toasty.error(context,"Same as Old",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }

                for (i in list.indices) {
                    if (list[i]?.adnName == binding.addonNameEt.text.toString() && list[i]?.adnName != list[position]?.adnName) {
                        Toasty.error(context,"Already Available",Toast.LENGTH_SHORT,true).show()
                        return@setOnClickListener
                    }
                }

                list[position] = Addon(binding.addonNameEt.text.toString(), binding.addonPriceEt.text.toString().toDouble())
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