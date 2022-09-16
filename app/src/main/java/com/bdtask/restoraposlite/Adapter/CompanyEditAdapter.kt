package com.bdtask.restoraposlite.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cmpny
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogFoodLongClickBinding
import com.bdtask.restoraposlite.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposlite.databinding.VhEditDeleteBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompanyEditAdapter(private val context: Context,
                         private val list : MutableList<Cmpny>
                         ): RecyclerView.Adapter<CompanyEditAdapter.VHCompanyEdit>() {

    inner class VHCompanyEdit(val binding: VhEditDeleteBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHCompanyEdit {
        return VHCompanyEdit(VhEditDeleteBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_edit_delete,parent,false)))
    }

    override fun onBindViewHolder(holder: VHCompanyEdit, position: Int) {
        holder.binding.itemTv.text = list[position].cNm

        holder.binding.itemEditBtn.setOnClickListener {
            val dialog = Dialog(context)
            val binding = DialogSingleItemetBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_single_itemet,null))
            dialog.setContentView(binding.root)

            binding.itemTv.text = "Update Waiter"
            binding.itemEt.setText(list[position].cNm)
            binding.itemBtn.text = "Update"


            binding.root.setOnClickListener { Util.hideSoftKeyBoard(context,binding.root) }


            binding.itemCross.setOnClickListener { dialog.dismiss() }


            binding.itemBtn.setOnClickListener {
                if (binding.itemEt.text.toString().isEmpty()) {
                    binding.itemEt.setError("Empty Value Forbidden")
                    binding.itemEt.requestFocus()
                    return@setOnClickListener
                }

                if (binding.itemEt.text.toString() == list[position].cNm) {
                    Toast.makeText(context, "Same as Old", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                for (i in list.indices) {
                    if (list[i].cNm == binding.itemEt.text.toString()) {
                        Toast.makeText(context, "Already Available", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }


                GlobalScope.launch(Dispatchers.IO) {

                    database.AppDao().updateCompany(Cmpny(list[position].id, binding.itemEt.text.toString()))

                    withContext(Dispatchers.Main) {
                        Toasty.success(context, "Company Updated Successfully", Toast.LENGTH_SHORT, true).show()
                        dialog.dismiss()
                    }
                }
            }

            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        holder.binding.itemDltBtn.setOnClickListener {
            val dialog = Dialog(context)
            val dbinding = DialogFoodLongClickBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_food_long_click,null))
            dialog.setContentView(dbinding.root)
            dbinding.msgTv.text = "Are you sure that you want to Delete this ?"
            dbinding.dltBtn.text = "No"
            dbinding.editBtn.text = "Yes"
            dbinding.dltBtn.setOnClickListener {
                dialog.dismiss()
            }

            dbinding.editBtn.setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {

                    database.AppDao().deleteCompany(list[position])

                    withContext(Dispatchers.Main) {
                        Toasty.success(context, "Company Deleted Successfully", Toast.LENGTH_SHORT, true).show()
                        dialog.dismiss()
                    }
                }
            }

            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6*width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}