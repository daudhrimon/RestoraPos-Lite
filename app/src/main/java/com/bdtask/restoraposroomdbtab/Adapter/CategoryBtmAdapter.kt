package com.bdtask.restoraposroomdbtab.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Room.Entity.Category
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposroomdbtab.databinding.VhEditDeleteBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryBtmAdapter(private val context: Context,
                         private var list: MutableList<Category>): RecyclerView.Adapter<CategoryBtmAdapter.CategoryBtmVH>() {

    inner class CategoryBtmVH(binding: VhEditDeleteBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryBtmVH {
        return CategoryBtmVH(VhEditDeleteBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_edit_delete,parent,false)))
    }

    override fun onBindViewHolder(holder: CategoryBtmVH, position: Int) {
        holder.binding.itemTv.setText(list[position].fCategory)

        holder.binding.itemEditBtn.setOnClickListener {

            showEditDialog(position)
        }

        // this is for Delete button from BottomSheet
        holder.binding.itemDltBtn.setOnClickListener {

            showDeleteAlert(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // this method will show a dialog foe edit category
    private fun showEditDialog(position: Int) {
        val alert = AlertDialog.Builder(context)
        val binding = DialogSingleItemetBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_single_itemet,null))
        alert.setView(binding.root)
        val dialog = alert.create()
        binding.itemTv.text = "Update Category"
        binding.itemEt.setText(list[position].fCategory)
        binding.itemBtn.text = "Update"
        dialog.show()

        binding.root.setOnClickListener { Util.hideSoftKeyBoard(context,binding.root) }

        binding.itemCross.setOnClickListener { dialog.dismiss() }

        binding.itemBtn.setOnClickListener {
            if (binding.itemEt.text.toString().isEmpty()){
                binding.itemEt.setError("Empty Value Forbidden")
                binding.itemEt.requestFocus()
                return@setOnClickListener
            }

            if (binding.itemEt.text.toString() == list[position].fCategory){
                Toast.makeText(context,"Same as Old",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            for (i in list.indices){
                if (list[i].fCategory == binding.itemEt.text.toString()){
                    Toast.makeText(context,"Already Available",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            GlobalScope.launch {
                MainActivity.database.categoryDao().updateCategory(Category(list[position].id, binding.itemEt.text.toString()))
            }
            Toasty.success(context,"Successful", Toast.LENGTH_SHORT, true).show()
            dialog.dismiss()
        }
    }

    // this method will asking for delete confirmation
    private fun showDeleteAlert(position: Int) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Delete Alert !")
        alert.setMessage("Are you sure,that you want to delete this ?")

        alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })

        alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            GlobalScope.launch {
                MainActivity.database.categoryDao().deleteCategory(Category(list[position].id,list[position].fCategory))
            }
            Toasty.success(context,"Successful", Toast.LENGTH_SHORT, true).show()
        })

        alert.show()
    }
}