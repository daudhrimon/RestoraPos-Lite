package com.bdtask.restoraposlite.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.MainActivity.Companion.foodList
import com.bdtask.restoraposlite.Room.Entity.Catgry
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposlite.databinding.VhItemEditBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.lang.Exception

class CategoryBtmAdapter(private val context: Context,
                         private var list: MutableList<Catgry>,
                         private val lifecycleScope: LifecycleCoroutineScope
                         ): RecyclerView.Adapter<CategoryBtmAdapter.CategoryBtmVH>() {

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

            if (foodList.isNotEmpty()) {
                for (i in foodList.indices) {
                    if (foodList[i].fCate == list[position].fCat) {
                        foodList[i].fCate = binding.itemEt.text.toString()

                        try {

                            lifecycleScope.launch(Dispatchers.IO) {

                                database.AppDao().updateFood(foodList[i])

                                //withContext(Dispatchers.Main) {
                                    if (i == foodList.size - 1) {

                                        lifecycleScope.launch(Dispatchers.IO) {
                                            database.AppDao().updateCategory(
                                                Catgry(
                                                    list[position].id,
                                                    binding.itemEt.text.toString()))

                                            withContext(Dispatchers.Main) {
                                                Toasty.success(context, "Successful", Toast.LENGTH_SHORT, true).show()
                                            }
                                        }
                                    }
                                //}
                            }
                        } catch (e: Exception) {/**/
                        }
                    }
                }

            } else {

                lifecycleScope.launch(Dispatchers.IO) {
                    database.AppDao().updateCategory(
                        Catgry(
                            list[position].id,
                            binding.itemEt.text.toString()
                        )
                    )

                    withContext(Dispatchers.Main) {
                        Toasty.success(context, "Successful", Toast.LENGTH_SHORT, true).show()
                    }
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