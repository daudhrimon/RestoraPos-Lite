package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.Adapter.SplitFoodAdapter
import com.bdtask.restoraposroomdbtab.Adapter.SplitterAdapter
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Model.Adns
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Room.Entity.Split
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitItemBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitOrderBinding

class SplitOrder(context: Context, var ongItem: Order,
                 private val foodCount: Int ): Dialog(context),SplitFoodClickListener {

    private val tmpOngItem = ongItem
    private lateinit var binding: DialogSplitOrderBinding
    private lateinit var dialog :Dialog
    private lateinit var spiBind: DialogSplitItemBinding
    private var spinnerList = arrayListOf<Int>()
    private var splitterCount = 0
    private var splitterList = mutableListOf<Split>()
    private var totalAdnPrice = 0.0
    private var splitAdnPrice = 0.0
    private var splitterIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSplitOrderBinding.bind(layoutInflater.inflate(R.layout.dialog_split_order,null))
        setContentView(binding.root)

        getInit()

        binding.spItemPlus.setOnClickListener {
            dialog.show()
        }

        binding.spClose.setOnClickListener {
            onBackPressed()
        }

        binding.splitItemSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                splitterCount = binding.splitItemSpnr.selectedItem.toString().toInt()

                // adding Split order id
                splitterList.clear()
                var id = 0
                for (i in 1..splitterCount){
                    id += 1
                    splitterList.add(Split(tmpOngItem.id.toString() + " ( $id )",tmpOngItem.id,0, CsInf("","",""), emptyList<Cart>().toMutableList()))
                }

                // this will divide total addon price by splitter Count
                splitAdnPrice = totalAdnPrice / splitterCount

                binding.spSplitterRv.adapter = SplitterAdapter(context,splitterList)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        Log.wtf("ONLIST",tmpOngItem.toString())
    }

    private fun setSplitterRecycler() {


    }

    private fun getInit() {
        dialog = Dialog(context)
        spiBind = DialogSplitItemBinding.bind(layoutInflater.inflate(R.layout.dialog_split_item,null))
        dialog.setContentView(spiBind.root)

        spiBind.spfItemRv.adapter = SplitFoodAdapter(context,tmpOngItem.cart,this)

        val width = context.resources.displayMetrics.widthPixels
        dialog.window?.setLayout((2 * width)/3, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        // this will count Total food
        for (i in 2..foodCount){
            spinnerList.add(i)
        }

        // this will count total addon price
        splitAdnPrice = 0.0
        for (i in ongItem.cart.indices){
            totalAdnPrice += ongItem.cart[i].adnPrc
        }

        // setting food count to spinner
        binding.splitItemSpnr.adapter = ArrayAdapter(context,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinnerList)
    }

    override fun onSplitFoodClick(position: Int) {
        if (splitterList[splitterIndex].cart.isEmpty()){
            val qnt = 1
            val prc = tmpOngItem.cart[position].varPrc * qnt
            val tUPrc = prc + splitAdnPrice
            splitterList[splitterIndex].cart.add(Cart(tmpOngItem.cart[position].title,
                tmpOngItem.cart[position].vari, tmpOngItem.cart[position].varPrc,
                qnt, prc, tUPrc, splitAdnPrice, emptyList<Adns>().toMutableList(),""))
        } else {
            if (splitterList[splitterIndex].cart[position].title == tmpOngItem.cart[position].title
                && splitterList[splitterIndex].cart[position].vari == tmpOngItem.cart[position].vari){

                val qnt = splitterList[splitterIndex].cart[position].fQnty + 1
                val prc = splitterList[splitterIndex].cart[position].varPrc * qnt
                val tUPrc = prc + splitAdnPrice

                splitterList[splitterIndex].cart[position].fQnty = qnt
                splitterList[splitterIndex].cart[position].fPrc = prc
                splitterList[splitterIndex].cart[position].tUPrc = tUPrc
            } else {
                val qnt = 1
                val prc = splitterList[splitterIndex].cart[position].varPrc * qnt
                val tUPrc = prc + splitAdnPrice

                splitterList[splitterIndex].cart.add(Cart(tmpOngItem.cart[position].title,
                    tmpOngItem.cart[position].vari,tmpOngItem.cart[position].varPrc,
                    qnt, prc, tUPrc, splitAdnPrice, emptyList<Adns>().toMutableList(),""))
            }
        }
        Log.wtf("ABCD",splitterList.toString())
        Log.wtf("ABCWWWW",splitAdnPrice.toString())
    }
}