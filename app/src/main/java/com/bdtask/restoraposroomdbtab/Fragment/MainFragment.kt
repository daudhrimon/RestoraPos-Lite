package com.bdtask.restoraposroomdbtab.Fragment

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bdtask.restoraposroomdbtab.Dialog.Calculator
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Adapter.*
import com.bdtask.restoraposroomdbtab.Interface.CartClickListener
import com.bdtask.restoraposroomdbtab.Interface.FoodClickListener
import com.bdtask.restoraposroomdbtab.Model.*
import com.bdtask.restoraposroomdbtab.Printer.PrinterUtil.ESCUtil.boldOff
import com.bdtask.restoraposroomdbtab.Printer.PrinterUtil.ESCUtil.boldOn
import com.bdtask.restoraposroomdbtab.Printer.PrinterUtil.SunmiPrintHelper
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogCloseAlertBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogFoodClickedBinding
import com.bdtask.restoraposroomdbtab.databinding.FragmentMainBinding
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.exceptions.EscPosEncodingException
import com.dantsu.escposprinter.exceptions.EscPosParserException
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.sunmi.peripheral.printer.SunmiPrinterService
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.lang.Exception

class MainFragment : Fragment(), FoodClickListener, CartClickListener {
    private lateinit var mainBinding: FragmentMainBinding
    private var categoryList = mutableListOf<String>()
    private var variantNameList = mutableListOf<String>()
    private var grandTotal = 0.0
    private var foodVariant = ""
    private var variantPrice = 0.0
    private var foodQuantity = 1
    private var foodPrice = 0.0
    private var totalUnitPrice = 0.0
    private var homeAddonList = mutableListOf<HomeAddon>()
    private var cartList = mutableListOf<FoodCart>()
    private var sharedPref = SharedPref
    private lateinit var printHelper: SunmiPrintHelper
    private var orderId: Long? = null
    private var token = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())

        printerInit()

        // getting and setting category Recycler
        MainActivity.database.categoryDao().getCategories().observe(viewLifecycleOwner, Observer{
            categoryList.clear()
            categoryList = it.toMutableList()

            if (categoryList.size > 0){
                categoryList.add(0,"All Category")
                mainBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            } else {
                categoryList.add(0,"There is No Category Found")
                mainBinding.tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
            }

            mainBinding.viewPager2.adapter = CategoryAdapter(requireContext(),categoryList,this)

            TabLayoutMediator(mainBinding.tabLayout, mainBinding.viewPager2) {tab,position->
                tab.text = categoryList[position]
            }.attach()
        })

        // setting cart Recycler Adapter
        setCartRecyclerAdapter()

        // menu Button Click
        mainBinding.menuBtn.setOnClickListener {
            MainActivity.drawerLayout.open()
        }

        // PLUS Button Click
        mainBinding.orderCustomize.setOnClickListener{
            findNavController().navigate(R.id.homeFrag2orderInfoFrag)
        }

        // OnGoing Button Click
        mainBinding.ongoingTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2ongoingFrag)
        }

        // Today Button Click
        mainBinding.todayTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2todayFrag)
        }

        // close Button Click
        mainBinding.closeTv.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val binding = DialogCloseAlertBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_close_alert,null))
            dialog.setContentView(binding.root)
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            /*binding.logout.setOnClickListener {
                /////////////////////////////////////////////////////
                //findNavController().navigate(R.id.to_loginFragment)
                dialog.dismiss()
            }
            binding.closeCounter.setOnClickListener {
                dialog.dismiss()
            }*/
        }

        // Calculator BUTTON Click
        mainBinding.calculatorLay.setOnClickListener {
            val dialog: Dialog = Calculator(requireContext())
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((6 * width)/7, (6 * height)/7)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // cart Delete Click
        mainBinding.deleteBtn.setOnClickListener {
            if (sharedPref.readSharedCartList() == null || sharedPref.readSharedCartList()!!.isEmpty()){
                Toasty.info(requireContext(), "Nothing To Delete !", Toast.LENGTH_SHORT, true).show()
            } else {
                sharedPref.writeSharedCartList(emptyList<FoodCart>())
                setCartRecyclerAdapter()
                Toasty.success(requireContext(),"Delete Successful",Toast.LENGTH_SHORT,true).show()
            }
        }

        // quick order Click Handler
        mainBinding.quickOrder.setOnClickListener {
            quickOrderClickHandler()
        }

        // placeOrder Click Handler
        mainBinding.placeOrder.setOnClickListener {
            placeOrderClickHandler()
        }

        // Nav Drawer Controller
        MainActivity.navDrawer.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.addFood -> findNavController().navigate(R.id.homeFrag2foodFrag)
            }
            MainActivity.drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }

        // return View
        return mainBinding.root
    }



    private fun quickOrderClickHandler() {
        val data = Util.getDate()
        println(data)
    }



    // place order ClickHandler
    private fun placeOrderClickHandler() {
        var tempCartList = mutableListOf<FoodCart>()
        var orderInfo = OrderInfo(CustomerInfo("","",""),"","","","","")
        if (sharedPref.readSharedCartList() != null){
            tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
        }

        if (sharedPref.readSharedOrderInfoList() != null){
            orderInfo = sharedPref.readSharedOrderInfoList()!!
        }

        if (tempCartList.isNotEmpty()) {
            if (orderInfo.customerInfo.cusName.isNotEmpty() &&
                    orderInfo.customerType.isNotEmpty()){

                // token to SharedPref
                var lToken: Long = 1
                if (sharedPref.getSharedToken() != null){
                    lToken = sharedPref.getSharedToken()!!
                    sharedPref.setSharedToken(lToken)
                } else {
                    sharedPref.setSharedToken(lToken)
                }
                token = if (lToken in 1..9){
                    "0$lToken"
                } else {
                    lToken.toString()
                }

                try {
                    Log.wtf("CART",tempCartList.toString())
                    GlobalScope.launch {
                        orderId = MainActivity.database.orderDao().insertOrder(Order(0,0,0,0,Util.getDate().toString(),token,orderInfo,tempCartList.toList()))
                    }
                    Toasty.success(requireContext(),"Successful",Toast.LENGTH_SHORT,true).show()
                } catch (e:Exception){
                    Toasty.success(requireContext(),e.message.toString(),Toast.LENGTH_SHORT,true).show()
                }

                showTokenPrintDialog()

            } else {
                mainBinding.focusLottie.visibility = View.VISIBLE
                Toasty.error(requireActivity(), "Set Order Info First !", Toast.LENGTH_SHORT, true).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    mainBinding.focusLottie.visibility = View.GONE
                }, 5000)
            }
        } else {
            Toasty.error(requireActivity(),"Add Food To Cart First !", Toast.LENGTH_SHORT,true).show()
        }
    }



    // asking for print token
    private fun showTokenPrintDialog() {
       val printDialog = SweetAlertDialog(requireContext(),SweetAlertDialog.SUCCESS_TYPE)
        printDialog.titleText = "Do you want to print Token ?"
        printDialog.confirmText = "Yes"
        printDialog.cancelText = "No"

        printDialog.setCancelClickListener {
            sharedPref.writeSharedCartList(emptyList<FoodCart>())
            it.dismissWithAnimation()
            setCartRecyclerAdapter()
        }.setConfirmClickListener {
            it.dismissWithAnimation()
            printToken(printDialog)
        }.show()
    }



    // print Token
    private fun printToken(printDialog: SweetAlertDialog) {
        var tempCartList = mutableListOf<FoodCart>()
        var orderInfo = OrderInfo(CustomerInfo("","",""),"","","","","")

        if (sharedPref.readSharedCartList() != null){
            tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
        }
        if (sharedPref.readSharedOrderInfoList() != null){
            orderInfo = sharedPref.readSharedOrderInfoList()!!
        }

        if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {
            val sunmiPrinterService: SunmiPrinterService? = printHelper.sunmiPrinterService

            //Sunmi Printer

            val txt = arrayOf("Daud", "Hoshen")
            val width = intArrayOf(1, 1)
            val align = intArrayOf(0, 2)
            sunmiPrinterService!!.setAlignment(1, null)
            sunmiPrinterService!!.printTextWithFont("Token :\b$token", null, 42f, null)

            sunmiPrinterService.printText("\n", null)

            txt[0] = orderInfo.customerInfo.cusName
            txt[1] = orderInfo.waiter
            sunmiPrinterService.printColumnsString(
                txt,
                width, align, null
            )

            sunmiPrinterService.sendRAWData(boldOn(), null)
            val itemss = arrayOf("Items","Size")
            sunmiPrinterService.printColumnsString(
                itemss,
                width, align, null
            )
            sunmiPrinterService!!.printTextWithFont("\n", null, 28f, null)

            val items = arrayOf("", "")
            sunmiPrinterService!!.setAlignment(0, null)
            for (i in tempCartList.indices) {

                sunmiPrinterService.sendRAWData(boldOn(), null)

                sunmiPrinterService!!.printTextWithFont(
                    tempCartList[i].foodTitle + "\n",
                    null,
                    28f,
                    null
                )
                sunmiPrinterService.setFontSize(26f,null)
                sunmiPrinterService.sendRAWData(boldOff(), null)
                val items = arrayOf("x"+tempCartList[i].foodQuantity,tempCartList[i].foodVariant )
                sunmiPrinterService.printColumnsString(
                    items,
                    width, align, null
                )

                if (tempCartList[i].addonList.size > 0) {
                    val addonsList = tempCartList[i].addonList

                    for (k in addonsList.indices) {
                        val addonItem = arrayOf(addonsList[k].adnName,""+addonsList[k].adnQuantity)
                        sunmiPrinterService.printColumnsString(
                            addonItem,
                            width, align, null)
                    }
                }

                if (tempCartList[i].note.isNotEmpty()){
                    sunmiPrinterService.printTextWithFont(
                        tempCartList[i].note+"\n" , null, 28f, null
                    )
                }

            }
            sunmiPrinterService.printTextWithFont("\n", null, 28f, null)
            sunmiPrinterService.sendRAWData(boldOn(), null)
            if (orderInfo.table.isNotEmpty()) {
                items[0] = "Order:$orderId"
                items[1] = "Table:" + orderInfo.table
                sunmiPrinterService.printColumnsString(
                    items,
                    width, align, null
                )
            }else{
                items[0] = "Order:$orderId"
                items[1] = ""
                sunmiPrinterService.printColumnsString(
                    items, width, align, null
                )
            }

            sunmiPrinterService.printText("\n", null)

            SunmiPrintHelper.getInstance().feedPaper()

        } else {

            //ESCPOS-ThermalPrinter

            var printer: EscPosPrinter? = null
            try {
                printer = EscPosPrinter(
                    BluetoothPrintersConnections.selectFirstPaired(),
                    180, 78f, 45, EscPosCharsetEncoding("windows-1252", 16)
                )
            } catch (e: EscPosConnectionException) {
                e.printStackTrace()
            }
            try {
                printer!!.printFormattedTextAndCut(
                    "[C]<b><font size='big'>Token No:" + token + "</font></b>\n"
                            + "[C]" + orderInfo.customerInfo.cusName
                            + "[L]\n" +
                            "[L]<b>Items</b>" + "[R]Size<b></b>\n" +
                            "[L]\n" +
                            tokenLoopData(tempCartList) +
                            "[L]\n" +
                            "[L]Order No: " + orderId + "[R] " + orderInfo.table + "\n"
                )
            } catch (e: EscPosConnectionException) {
                e.printStackTrace()
            } catch (e: EscPosParserException) {
                e.printStackTrace()
            } catch (e: EscPosEncodingException) {
                e.printStackTrace()
            } catch (e: EscPosBarcodeException) {
                e.printStackTrace()
            }
        }
        sharedPref.writeSharedCartList(emptyList())
        printDialog.dismissWithAnimation()
        orderId = null
        setCartRecyclerAdapter()
    }


    // token loop Data
    private fun tokenLoopData(list: MutableList<FoodCart>): String {
        var items = ""
        var adOnPrice = 0.0
        for (i in list.indices) {
            items = "" + items + "[L]<b>" + list[i].foodTitle + "</b>\n" +
                    "[L]"+"x" + list[i].foodQuantity + "[R]<b>" +  list[i].foodVariant +  "</b>\n"

            if (list[i].addonList.size > 0) {
                val addonsList = list[i].addonList
                for (k in addonsList.indices) {
                    adOnPrice = addonsList[k].adnPrice.toDouble() * addonsList[k].adnQuantity
                    items = "" + items + "" + "[L]" + addonsList[k].adnName + "x" + addonsList[k].adnQuantity +"\n"
                }
            }

            if  (list[i].note != ""){
                items = items+ "[L]<b>" + list[i].foodTitle + "</b>\n"
            }
        }
        return items
    }



    // show alert Dialog BasedOn Food Item Click
    override fun onFoodClick( foodId: Long?, foodTitle: String?, variantlist: List<Variant>, addonList: List<Addon> ) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val foodDialogBinding = DialogFoodClickedBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_food_clicked,null))
        dialog.setContentView(foodDialogBinding.root)

        foodVariant = ""
        variantPrice = 0.0
        foodQuantity = 1
        foodPrice = 0.0
        totalUnitPrice = 0.0
        homeAddonList.clear()
        var tempCartList = mutableListOf<FoodCart>()
        val newAddonList = mutableListOf<HomeAddon>()



        foodDialogBinding.dcFoodName.text = foodTitle

        variantNameList.clear()
        for (i in variantlist.indices){
            variantNameList.add(variantlist[i].variant)
        }

        foodDialogBinding.dcVariantSpinner.adapter = context?.let { ArrayAdapter(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, variantNameList) }

        foodDialogBinding.dcVariantSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {

                foodVariant = foodDialogBinding.dcVariantSpinner.selectedItem.toString()

                for (i in variantlist.indices){
                    if (variantlist[i].variant == foodVariant){

                        variantPrice = variantlist[i].fPrice

                        foodQuantity = 1
                        foodDialogBinding.dcQuantity.text = foodQuantity.toString()

                        foodPrice = variantPrice

                        totalUnitPrice = variantPrice
                        foodDialogBinding.dcFoodPrice.text = totalUnitPrice.toString()
                        homeAddonList.clear()
                        foodDialogBinding.addonsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        if (addonList.isNotEmpty()){
            foodDialogBinding.addonsRecycler.visibility = View.VISIBLE
            foodDialogBinding.addonsRecycler.adapter = AddonsAdapter(requireContext(), addonList, foodDialogBinding, homeAddonList)
        }

        foodDialogBinding.dcCrossBtn.setOnClickListener {
            dialog.dismiss()
        }

        foodDialogBinding.dcPlusBtn.setOnClickListener {
            var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = foodDialogBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity < 99){

                foodQuantity += 1
                foodDialogBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice += variantPrice
                foodDialogBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }

        foodDialogBinding.dcMinusBtn.setOnClickListener {
            var foodPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = foodDialogBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity > 1){

                foodQuantity -= 1
                foodDialogBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice -= variantPrice
                foodDialogBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }



        // add To Cart Button Click
        foodDialogBinding.addToCartBtn.setOnClickListener {
            var haveToInsert = true
            var addonChecker = 0
            var addonsPrice = 0.0
            tempCartList.clear()
            newAddonList.clear()
            totalUnitPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
            foodPrice = variantPrice * foodQuantity

            if (sharedPref.readSharedCartList() != null){
                tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
            }
            if (homeAddonList.size > 0){
                addonChecker = 1
            }
            if (tempCartList.size > 0) {
                for (i in tempCartList.indices) {
                    if (tempCartList[i].foodTitle == foodTitle &&
                        tempCartList[i].foodVariant == foodVariant) {
                        if (addonChecker == 1) {
                            if (Gson().toJson(tempCartList[i].addonList).contains(Gson().toJson(homeAddonList))) {
                                for (h in homeAddonList.indices) {
                                    addonsPrice += homeAddonList[h].adnPrice
                                }
                                tempCartList[i].foodQuantity += foodQuantity
                                tempCartList[i].foodPrice += foodPrice
                                tempCartList[i].totalUnitPrice += totalUnitPrice
                                tempCartList[i].addonsPrice += addonsPrice
                                //marge Addons by Name
                                val addonListByName = homeAddonList.associateBy { it.adnName}
                                tempCartList[i].addonList.forEach { tempAdn ->
                                    addonListByName[tempAdn.adnName]?.let { homeAdn ->
                                        tempAdn.adnPrice += homeAdn.adnPrice
                                        tempAdn.adnQuantity += homeAdn.adnQuantity
                                    }
                                }
                                haveToInsert = false
                            } else {
                                haveToInsert = true
                            }
                        } else {
                            tempCartList[i].foodQuantity += foodQuantity
                            tempCartList[i].foodPrice += foodPrice
                            tempCartList[i].totalUnitPrice += totalUnitPrice
                            haveToInsert = false
                        }
                        break
                    } else {
                        haveToInsert = true
                    }
                }
            }
            if (haveToInsert){
                tempCartList.add(FoodCart(foodTitle!!, foodVariant, variantPrice, foodQuantity, foodPrice, totalUnitPrice, addonsPrice, homeAddonList,""))
            }
            sharedPref.writeSharedCartList(tempCartList.toList())
            setCartRecyclerAdapter()
            dialog.dismiss()
        }


        // add multiple button click
        foodDialogBinding.addMultiBtn.setOnClickListener {
            /*var haveToInsert = true
            var checkCounter = 0*/
            var addonsPrice = 0.0
            tempCartList.clear()
            totalUnitPrice = foodDialogBinding.dcFoodPrice.text.toString().toDouble()
            foodPrice = variantPrice * foodQuantity

            if (sharedPref.readSharedCartList() != null){
                tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
            }
            //val tempAddonList = mutableListOf<String>()
            if (homeAddonList.size > 0){
                for (i in homeAddonList.indices){
                    addonsPrice += homeAddonList[i].adnPrice
                }
            }
            tempCartList.add(FoodCart(foodTitle!!, foodVariant, variantPrice, foodQuantity, foodPrice, totalUnitPrice, addonsPrice, homeAddonList,""))
            sharedPref.writeSharedCartList(tempCartList.toList())
            setCartRecyclerAdapter()
        }

        dialog.setCancelable(false)
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6*width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    // setting cart Recycler Adapter
    private fun setCartRecyclerAdapter() {
        if (sharedPref.readSharedCartList() != null) {
            cartList.clear()
            cartList = sharedPref.readSharedCartList()!!.toMutableList()

            if (cartList.size > 0) {
                mainBinding.cartRecyclerLay.visibility = View.VISIBLE
                mainBinding.cartRecycler.visibility = View.VISIBLE
                mainBinding.cartRecycler.adapter = CartAdapter(requireContext(), cartList, this)

                grandTotal = 0.0
                for (i in cartList.indices){
                    grandTotal += cartList[i].totalUnitPrice
                }
                mainBinding.grandTotalTv.text = grandTotal.toString()

            } else {
                mainBinding.cartRecyclerLay.visibility = View.GONE
                mainBinding.cartRecycler.visibility = View.GONE
            }
        }
    }


    // set CardRecycler on Cart Item Delete
    override fun onCartReload() {
        setCartRecyclerAdapter()
    }


    // init Printer
    private fun printerInit() {
        if (Util.getPrinterDevice(BluetoothAdapter.getDefaultAdapter()) == true) {
            SunmiPrintHelper.getInstance().initSunmiPrinterService(requireContext())
            printHelper = SunmiPrintHelper.getInstance()
            printHelper.initSunmiPrinterService(requireContext())
        }
    }
}