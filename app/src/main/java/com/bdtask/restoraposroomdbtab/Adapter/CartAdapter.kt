package com.bdtask.restoraposroomdbtab.Adapter

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Interface.CartClickListener
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogNoteBinding
import com.bdtask.restoraposroomdbtab.databinding.VhCartItemBinding

class CartAdapter(private val context: Context,
                  private var cartList: MutableList<FoodCart>,
                  private val cartClickListener: CartClickListener): RecyclerView.Adapter<CartAdapter.CartVH>() {

    var grandTotal = 0.0

    inner class CartVH(binding: VhCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        return CartVH(VhCartItemBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_cart_item, parent, false)))
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.binding.foodItem.text = cartList[position].foodTitle
        holder.binding.foodVariant.text = cartList[position].foodVariant
        holder.binding.foodQuantity.text = cartList[position].foodQuantity.toString()
        holder.binding.totalUnitPrice.text = cartList[position].totalUnitPrice.toString()

        if (cartList[position].note.isNotEmpty()){
            holder.binding.noteTv.visibility = View.VISIBLE
        } else {
            holder.binding.noteTv.visibility = View.GONE
        }
        holder.binding.noteTv.text = cartList[position].note

        holder.binding.plusBtnC.setOnClickListener {
            var currentTotalUnitPrice = holder.binding.totalUnitPrice.text.toString().toDouble()
            var currentQuantity = holder.binding.foodQuantity.text.toString().toInt()

            if (currentQuantity < 99){
                currentQuantity += 1
                holder.binding.foodQuantity.text = currentQuantity.toString()

                currentTotalUnitPrice += cartList[position].variantPrice
                holder.binding.totalUnitPrice.text = currentTotalUnitPrice.toString()

                cartList[position].foodQuantity = currentQuantity
                cartList[position].totalUnitPrice = currentTotalUnitPrice

                SharedPref.init(context)
                SharedPref.writeSharedCartList(cartList.toList())

                cartClickListener.onCartReload()
            }
        }

        holder.binding.minusBtnC.setOnClickListener {
            var currentTotalUnitPrice = holder.binding.totalUnitPrice.text.toString().toDouble()
            var currentQuantity = holder.binding.foodQuantity.text.toString().toInt()

            if (currentQuantity > 1){
                currentQuantity -= 1
                holder.binding.foodQuantity.text = currentQuantity.toString()

                currentTotalUnitPrice -= cartList[position].variantPrice
                holder.binding.totalUnitPrice.text = currentTotalUnitPrice.toString()

                cartList[position].foodQuantity = currentQuantity
                cartList[position].totalUnitPrice = currentTotalUnitPrice

                SharedPref.init(context)
                SharedPref.writeSharedCartList(cartList.toList())

                cartClickListener.onCartReload()
            }
        }

        holder.binding.deleteBtnC.setOnClickListener {
            cartList.removeAt(position)
            SharedPref.init(context)
            SharedPref.writeSharedCartList(cartList.toList())

            cartClickListener.onCartReload()
        }

        holder.itemView.setOnLongClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val ndBinding = DialogNoteBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_note,null))
            dialog.setContentView(ndBinding.root)

            if ( cartList[position].note.isNotEmpty()){
                ndBinding.addNoteBtn.setText("Update Note")
                ndBinding.noteEt.setText(cartList[position].note)
            }

            ndBinding.root.setOnClickListener { Util.hideSoftKeyBoard(context, ndBinding.root) }

            ndBinding.cancelBtn.setOnClickListener { dialog.dismiss() }

            ndBinding.addNoteBtn.setOnClickListener {
               if (ndBinding.noteEt.text.toString().isEmpty()){
                    /*ndBinding.noteEt.setError("Empty Note Forbidden")
                    ndBinding.noteEt.requestFocus()*/
                   cartList[position].note = ""
                } else {
                   cartList[position].note = ndBinding.noteEt.text.toString()
                }

                SharedPref.init(context)
                SharedPref.writeSharedCartList(cartList.toList())

                notifyDataSetChanged()

                dialog.dismiss()
            }

            dialog.show()
            val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display: Display = wm.getDefaultDisplay()
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            val win = dialog.window
            win!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}