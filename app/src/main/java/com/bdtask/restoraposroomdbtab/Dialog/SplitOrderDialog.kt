package com.bdtask.restoraposroomdbtab.Dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bdtask.restoraposroomdbtab.Adapter.SplitFoodAdapter
import com.bdtask.restoraposroomdbtab.Adapter.SplitterAdapter
import com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener
import com.bdtask.restoraposroomdbtab.Interface.SplitterClickListener
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.Model.OdrInf
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Room.Entity.Split
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitItemBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogSplitOrderBinding
import es.dmoral.toasty.Toasty

class SplitOrderDialog(context: Context,
                       private val sharedPref: SharedPref,
                       private val foodCount: Int ): Dialog(context), SplitFoodClickListener, SplitterClickListener {

    private var tmpOngItem = sharedPref.readOrder()!!
    private lateinit var binding: DialogSplitOrderBinding
    private lateinit var dialog :Dialog
    private lateinit var spiBind: DialogSplitItemBinding
    private var spinnerList = arrayListOf<Int>()
    private var splitterCount = 0
    private var splitterList = mutableListOf<Split>()
    private var perAddonPriceList = arrayListOf<Double>()
    companion object { var splitterIndex = -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSplitOrderBinding.bind(layoutInflater.inflate(R.layout.dialog_split_order,null))
        setContentView(binding.root)

        getCartAndSetPlusFood()

        setSpinnerAdapter()

        binding.spItemPlus.setOnClickListener {
            dialog.show()
        }

        binding.spClose.setOnClickListener {
            dismiss()
        }


        binding.splitItemSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                splitterCount = binding.splitItemSpnr.selectedItem.toString().toInt()

                // adding Split order id
                splitterList.clear()
                var id = 0
                for (i in 1..splitterCount){
                    id += 1
                    splitterList.add(Split(tmpOngItem.id.toString() + " ( $id )",tmpOngItem.id,0,
                        CsInf("","",""),0.0,0.0, 0.0,0.0,
                        emptyList<Cart>().toMutableList(),emptyList<Pay>().toMutableList()))
                }

                getCartAndSetPlusFood()

                setSplitterRecycler()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }


        binding.custmrPlus.setOnClickListener {
            val dialog = AddCustomerDialog(context,0)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            val width = context.resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun getCartAndSetPlusFood() {
        tmpOngItem = sharedPref.readOrder()!!
        dialog = Dialog(context)
        spiBind = DialogSplitItemBinding.bind(layoutInflater.inflate(R.layout.dialog_split_item,null))
        dialog.setContentView(spiBind.root)

        spiBind.spfItemRv.adapter = SplitFoodAdapter(context, tmpOngItem.cart,this)

        val width = context.resources.displayMetrics.widthPixels
        dialog.window?.setLayout((3 * width)/4, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setSplitterRecycler() {
        binding.spSplitterRv.adapter = SplitterAdapter(context, splitterList, this)
    }

    private fun setSpinnerAdapter() {
        var price = 0.0
        for (i in tmpOngItem.cart.indices){
            price += tmpOngItem.cart[i].tUPrc
        }
        Log.wtf("PRICE",price.toString())

        // this will count Total food
        for (i in 2..foodCount){
            spinnerList.add(i)
        }

        // this will count total addon price
        for (i in tmpOngItem.cart.indices){
            if (tmpOngItem.cart[i].fQnty > 1){
                val perAdn = tmpOngItem.cart[i].adnPrc / tmpOngItem.cart[i].fQnty
                perAddonPriceList.add(perAdn)
            } else {
                perAddonPriceList.add(tmpOngItem.cart[i].adnPrc)
            }
        }
        Log.wtf("perAddonPriceList",perAddonPriceList.toString())

        // setting food count to spinner
        binding.splitItemSpnr.adapter = ArrayAdapter(context,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinnerList)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onSplitFoodClick(position: Int) {

        if (splitterIndex == -1) {

            Toasty.warning(context,"Please Select an Order First",Toasty.LENGTH_SHORT).show()

        }else{

            if (tmpOngItem.cart[position].fQnty > 0) {
                tmpOngItem.cart[position].fQnty -= 1

                if (splitterList[splitterIndex].cart.isEmpty()) {

                    val prc = tmpOngItem.cart[position].varPrc
                    val adnPrice = perAddonPriceList[position]
                    val tUPrc = prc + adnPrice

                    splitterList[splitterIndex].cart.add(Cart(tmpOngItem.cart[position].title,
                        tmpOngItem.cart[position].vari, tmpOngItem.cart[position].varPrc,
                        1, prc, tUPrc, adnPrice, tmpOngItem.cart[position].adns, ""
                    ))

                    Log.wtf("EmptyInsert", "iam here")

                } else {

                    var checker = true
                    var insert = true

                    for (i in splitterList[splitterIndex].cart.indices) {

                        if (checker) {

                            if (splitterList[splitterIndex].cart[i].title == tmpOngItem.cart[position].title
                            && splitterList[splitterIndex].cart[i].vari == tmpOngItem.cart[position].vari
                                && splitterList[splitterIndex].cart[i].adns == tmpOngItem.cart[position].adns) {

                                val varPrc = splitterList[splitterIndex].cart[i].varPrc
                                splitterList[splitterIndex].cart[i].fQnty += 1
                                splitterList[splitterIndex].cart[i].fPrc += varPrc
                                splitterList[splitterIndex].cart[i].adnPrc += perAddonPriceList[position]
                                val fPrc = splitterList[splitterIndex].cart[i].fPrc
                                val adnPrc = splitterList[splitterIndex].cart[i].adnPrc
                                val tUPrc = fPrc + adnPrc
                                splitterList[splitterIndex].cart[i].tUPrc = tUPrc

                                checker = false
                                insert = false

                                Log.wtf("Name and var matching", "iam here")

                            } else {
                                insert = true
                            }
                        }
                    }

                    if (insert) {

                        val prc = tmpOngItem.cart[position].varPrc
                        val adnPrice = perAddonPriceList[position]
                        val tUPrc = prc + adnPrice

                        splitterList[splitterIndex].cart.add(Cart(tmpOngItem.cart[position].title,
                            tmpOngItem.cart[position].vari, tmpOngItem.cart[position].varPrc,
                            1, prc, tUPrc, adnPrice, tmpOngItem.cart[position].adns, ""))

                        Log.wtf("Another Insert", "iam here")
                    }
                }
                binding.spSplitterRv.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onPayNowClickListener() {
        var spCount = this.splitterCount
        var spFoodCount = 0
        for (s in splitterList.indices){
            if (splitterList[s].cart.isNotEmpty()){
                spCount--
            }
        }
        for (i in tmpOngItem.cart.indices){
            spFoodCount += tmpOngItem.cart[i].fQnty
        }
        if (spCount == 0){

            if (spFoodCount == 0){

                val dialog = SplitPaymentDialog(context,1,
                    Order(
                        0, 0, 0, 0, "", "", 0.0, 0.0, 0.0, 0.0,
                        sharedPref.readOperator() ?: "",
                        OdrInf(CsInf("", "", ""), "", "", "", "", ""),
                        emptyList<Cart>().toMutableList(), emptyList<Pay>().toMutableList()), splitterList[splitterIndex]
                )

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.show()
                val width = context.resources.displayMetrics.widthPixels
                val height = context.resources.displayMetrics.heightPixels
                val win = dialog.window
                win!!.setLayout((9 * width)/10,(14 * height)/15)
                win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            } else {
                Toasty.warning(context,"You Have $spFoodCount Food Left to Split, Please Split Them First",Toasty.LENGTH_LONG).show()
            }
        } else {
            Toasty.warning(context, "You Have $spCount incomplete Split Left, Please Complete Them First",Toasty.LENGTH_LONG).show()
        }
    }
}