package com.bdtask.restoraposroomdbtab.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.database
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.foodList
import com.bdtask.restoraposroomdbtab.Room.Entity.Catgry
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposroomdbtab.databinding.VhEditDeleteBinding
import com.bdtask.restoraposroomdbtab.databinding.VhItemEditBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CategoryBtmAdapter(private val context: Context,
                         private var list: MutableList<Catgry>): RecyclerView.Adapter<CategoryBtmAdapter.CategoryBtmVH>() {

    inner class CategoryBtmVH(val binding: VhItemEditBinding): RecyclerView.ViewHolder(binding.root) {/**/}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryBtmVH {
        return CategoryBtmVH(VhItemEditBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_item_edit,parent,false)))
    }

    override fun onBindViewHolder(holder: CategoryBtmVH, position: Int) {

        holder.binding.itemTv.text = list[position].fCat

        holder.binding.itemEditBtn.setOnClickListener {

            showEditDialog(position)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // this method will show a dialog foe edit category
    private fun showEditDialog(position: Int) {
        val dialog = Dialog(context)
        val binding = DialogSingleItemetBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_single_itemet,null))
        dialog.setContentView(binding.root)

        binding.itemTv.text = "Update Category"
        binding.itemEt.setText(list[position].fCat)
        binding.itemBtn.text = "Update"


        binding.root.setOnClickListener { Util.hideSoftKeyBoard(context,binding.root) }

        binding.itemCross.setOnClickListener { dialog.dismiss() }

        binding.itemBtn.setOnClickListener {
            if (binding.itemEt.text.toString().isEmpty()){
                binding.itemEt.setError("Empty Value Forbidden")
                binding.itemEt.requestFocus()
                return@setOnClickListener
            }

            if (binding.itemEt.text.toString() == list[position].fCat){
                Toast.makeText(context,"Same as Old",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            for (i in list.indices){
                if (list[i].fCat == binding.itemEt.text.toString()){
                    Toast.makeText(context,"Already Available",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            var tempFoodList = mutableListOf<Food>()

            for (i in foodList.indices){
                if (foodList[i].fCate == list[position].fCat){
                    foodList[i].fCate = binding.itemEt.text.toString()

                    try {
                        GlobalScope.launch(Dispatchers.IO) {
                            database.foodDao().updateFood(foodList[i])

                            withContext(Dispatchers.Main){
                                if (i == foodList.size-1){

                                    GlobalScope.launch(Dispatchers.IO) {
                                        database.categoryDao().updateCategory(Catgry(list[position].id, binding.itemEt.text.toString()))

                                        withContext(Dispatchers.Main){
                                            Toasty.success(context,"Successful", Toast.LENGTH_SHORT, true).show()
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception){/**/}
                }
            }

            dialog.dismiss()
        }
        dialog.show()
        val width = context.resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}