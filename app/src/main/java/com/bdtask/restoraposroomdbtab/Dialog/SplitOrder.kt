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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bdtask.restoraposroomdbtab.Adapter.SplitFoodAdapter
import com.bdtask.restoraposroomdbtab.Adapter.SplitterAdapter
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.Model.OrderInfo
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitItemBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitOrderBinding

class SplitOrder(
    context: Context,
    private val ongList: Order,
    private val foodCount: Int ): Dialog(context),SplitFoodClickListener {

    private lateinit var binding: DialogSplitOrderBinding
    private lateinit var dialog :Dialog
    private lateinit var spiBind: DialogSplitItemBinding
    private var spinnerList = arrayListOf<Int>()
    private var splitterCount = 0
    private var splitterList = mutableListOf<Order>()
    private var idList = arrayListOf<String>()
    private var splitterIndex = -1

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

                setSplitterRecycler()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        Log.wtf("ONLIST",ongList.toString())
    }

    private fun setSplitterRecycler() {
        splitterList.clear()
        idList.clear()
        var id = 0
        for (i in 1..splitterCount){
            id += 1
            //idList.add(ongList.id.toString() + " ( $id )")
            //splitterList.add(Order(ongList.id.toString() + " ( $id )",ongList.status,ongList.date,ongList.token, emptyList(), emptyList()))
        }
        binding.spSplitterRv.adapter = SplitterAdapter(context,splitterList)
    }

    private fun getInit() {
        dialog = Dialog(context)
        spiBind = DialogSplitItemBinding.bind(layoutInflater.inflate(R.layout.dialog_split_item,null))
        dialog.setContentView(spiBind.root)

        spiBind.spfItemRv.layoutManager = LinearLayoutManager(context)
        spiBind.spfItemRv.adapter = SplitFoodAdapter(context,ongList.cartList,this)

        val width = context.resources.displayMetrics.widthPixels
        dialog.window?.setLayout((2 * width)/3, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        for (i in 2..foodCount){
            spinnerList.add(i)
        }

        binding.splitItemSpnr.adapter = ArrayAdapter(context,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinnerList)
    }

    override fun onSplitFoodClick(position: Int) {
      /*  if (splitterList[0].cartList.isEmpty()){
            splitterList[0].cartList = ongList.cartList
        }
        ongList.cartList.toMutableList()[position].foodQuantity -= 1
        splitterList[0].cartList.toMutableList()[position].foodQuantity += 1
        setSplitterRecycler()*/
    }

}