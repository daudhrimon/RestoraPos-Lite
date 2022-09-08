package com.bdtask.restoraposroomdbtab.Fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Adapter.*
import com.bdtask.restoraposroomdbtab.Dialog.*
import com.bdtask.restoraposroomdbtab.Interface.CartClickListener
import com.bdtask.restoraposroomdbtab.Interface.FoodClickListener
import com.bdtask.restoraposroomdbtab.Interface.TokenClickListener
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposroomdbtab.Model.*
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogCloseAlertBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogFoodClickedBinding
import com.bdtask.restoraposroomdbtab.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.lang.Exception

class MainFragment : Fragment(), FoodClickListener, CartClickListener, TokenClickListener {
    private lateinit var mainBinding: FragmentMainBinding
    private var categoryList = mutableListOf<String>()
    private var variantNameList = mutableListOf<String>()
    private var grandTotal = 0.0
    private var foodVariant = ""
    private var variantPrice = 0.0
    private var foodQuantity = 1
    private var foodPrice = 0.0
    private var totalUnitPrice = 0.0
    private var addonsList = mutableListOf<Adns>()
    private var cartList = mutableListOf<Cart>()
    private var sharedPref = SharedPref
    private var token = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)



        // getting and setting category Recycler
        MainActivity.database.categoryDao().getCategories().observe(viewLifecycleOwner, Observer{
            categoryList.clear()
            categoryList = it.toMutableList()

            if (categoryList.size > 0){
                categoryList.add(0,"All Category")
                mainBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            } else {
                categoryList.add(0,"There is No Food Category Found")
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
        }


        // Calculator BUTTON Click
        mainBinding.calculatorLay.setOnClickListener {
            val dialog: Dialog = CalculatorDialog(requireContext())
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
                sharedPref.writeSharedCartList(emptyList<Cart>().toMutableList())
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



        return mainBinding.root
    }


    private fun quickOrderClickHandler() {

        var tempCartList = mutableListOf<Cart>()
        var odrInf = OdrInf(CsInf("","",""),"","","","","")

        tempCartList = sharedPref.readSharedCartList() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readSharedOrderInfo() != null){
            odrInf = sharedPref.readSharedOrderInfo()!!
        }

        if (tempCartList.isNotEmpty()) {
            if (odrInf.csInf.csNm.isNotEmpty() &&
                odrInf.csTyp.isNotEmpty()){

                val order = Order(0,1,0,0,Util.getDate().toString(),"",0.0,
                    sharedPref.readVat() ?: 0.0, sharedPref.readCharge() ?: 0.0, 0.0,
                    odrInf,tempCartList, emptyList<Pay>().toMutableList())

                sharedPref.writeSharedCartList(emptyList<Cart>().toMutableList())

                sharedPref.writeSharedOrder(order)

                setCartRecyclerAdapter()

                // InVoice View Dialog
                val dialog = CPaymentDialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.show()
                val width = resources.displayMetrics.widthPixels
                val height = resources.displayMetrics.heightPixels
                val win = dialog.window
                win!!.setLayout((9 * width)/10,(14 * height)/15)
                win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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



    // place order ClickHandler
    private fun placeOrderClickHandler() {
        var tempCartList = mutableListOf<Cart>()
        var odrInf = OdrInf(CsInf("","",""),"","","","","")

        tempCartList = sharedPref.readSharedCartList() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readSharedOrderInfo() != null){
            odrInf = sharedPref.readSharedOrderInfo()!!
        }

        if (tempCartList.isNotEmpty()) {
            if (odrInf.csInf.csNm.isNotEmpty() &&
                    odrInf.csTyp.isNotEmpty()){

                token = Util.getToken(sharedPref)

                val order = Order(0, 0,0,0,
                    Util.getDate().toString(), token, 0.0,sharedPref.readVat() ?: 0.0, sharedPref.readCharge() ?: 0.0, 0.0, odrInf,tempCartList,
                    emptyList<Pay>().toMutableList())

                try {

                    GlobalScope.launch(Dispatchers.IO){

                        val orderId = MainActivity.database.orderDao().insertOrder(order)

                        withContext(Dispatchers.Main){

                            if (orderId != null && orderId.toString().isNotEmpty()){

                                Toasty.success(requireContext(),"Placed Order $orderId Successfully",Toast.LENGTH_SHORT,true).show()

                                // asking for print token
                                printToken(orderId)
                            }
                        }
                    }
                } catch (e:Exception){
                    Toasty.success(requireContext(),e.message.toString(),Toast.LENGTH_SHORT,true).show()
                }
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


    // Token dialog Click Listener

    override fun onTokenButtonsClick(tokenDialog: TokenDialog) {
        tokenDialog.dismissWithAnimation()
        setCartRecyclerAdapter()
    }


    // ask for print Token

    private fun printToken(orderId: Long) {
        var tempCartList = mutableListOf<Cart>()
        var odrInf = OdrInf(CsInf("","",""),"","","","","")

        tempCartList = sharedPref.readSharedCartList() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readSharedOrderInfo() != null){
            odrInf = sharedPref.readSharedOrderInfo()!!
        }

        TokenDialog(requireContext(),token,orderId,tempCartList,odrInf,this).show()

        sharedPref.writeSharedCartList(emptyList<Cart>().toMutableList())
    }



    // show alert Dialog BasedOn Food Item Click
    override fun onFoodClick(foodId: Long?, foodTitle: String?, variantlist: List<Variant>, adnList: List<Adn> ) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val fdBinding = DialogFoodClickedBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_food_clicked,null))
        dialog.setContentView(fdBinding.root)

        foodVariant = ""
        variantPrice = 0.0
        foodQuantity = 1
        foodPrice = 0.0
        totalUnitPrice = 0.0
        addonsList.clear()
        var tempCartList = mutableListOf<Cart>()
        val newAddonList = mutableListOf<Adns>()

        fdBinding.dcFoodName.text = foodTitle

        variantNameList.clear()
        for (i in variantlist.indices){
            variantNameList.add(variantlist[i].vari)
        }

        fdBinding.dcVariantSpinner.adapter = context?.let { ArrayAdapter(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, variantNameList) }

        fdBinding.dcVariantSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {

                foodVariant = fdBinding.dcVariantSpinner.selectedItem.toString()

                for (i in variantlist.indices){
                    if (variantlist[i].vari == foodVariant){

                        variantPrice = variantlist[i].fPrc

                        foodQuantity = 1
                        fdBinding.dcQuantity.text = foodQuantity.toString()

                        foodPrice = variantPrice

                        totalUnitPrice = variantPrice
                        fdBinding.dcFoodPrice.text = totalUnitPrice.toString()
                        addonsList.clear()
                        fdBinding.addonsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        if (adnList.isNotEmpty()){
            fdBinding.addonsRecycler.visibility = View.VISIBLE
            fdBinding.addonsRecycler.adapter = AddonsAdapter(requireContext(), adnList, fdBinding, addonsList)
        }

        fdBinding.dcCrossBtn.setOnClickListener {
            dialog.dismiss()
        }

        fdBinding.dcPlusBtn.setOnClickListener {
            var foodPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = fdBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity < 99){

                foodQuantity += 1
                fdBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice += variantPrice
                fdBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }

        fdBinding.dcMinusBtn.setOnClickListener {
            var foodPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = fdBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity > 1){

                foodQuantity -= 1
                fdBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice -= variantPrice
                fdBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }



        // add To Cart Button Click
        fdBinding.addToCartBtn.setOnClickListener {
            var haveToInsert = true
            var addonChecker = 0
            var addonsPrice = 0.0
            tempCartList.clear()
            newAddonList.clear()
            totalUnitPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodPrice = variantPrice * foodQuantity

            if (sharedPref.readSharedCartList() != null){
                tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
            }
            if (addonsList.size > 0){
                addonChecker = 1
                for (h in addonsList.indices) {
                    addonsPrice += addonsList[h].adnPrc
                }
            }

            if (tempCartList.size > 0) {

                for (i in tempCartList.indices) {

                    if (tempCartList[i].title == foodTitle &&
                        tempCartList[i].vari == foodVariant) {

                        if (addonChecker == 1) {

                            if (Gson().toJson(tempCartList[i].adns).contains(Gson().toJson(addonsList))) {
                                tempCartList[i].fQnty += foodQuantity
                                tempCartList[i].fPrc += foodPrice
                                tempCartList[i].tUPrc += totalUnitPrice
                                tempCartList[i].adnPrc += addonsPrice

                                //marge Addons by Name
                                val addonListByName = addonsList.associateBy { it.adnNm}
                                tempCartList[i].adns.forEach { tempAdn ->
                                    addonListByName[tempAdn.adnNm]?.let { homeAdn ->
                                        tempAdn.adnPrc += homeAdn.adnPrc
                                        tempAdn.adnQnty += homeAdn.adnQnty
                                    }
                                }
                                haveToInsert = false
                            } else {
                                haveToInsert = true
                            }
                        } else {
                            tempCartList[i].fQnty += foodQuantity
                            tempCartList[i].fPrc += foodPrice
                            tempCartList[i].tUPrc += totalUnitPrice
                            haveToInsert = false
                        }
                        break
                    } else {
                        haveToInsert = true
                    }
                }
            }
            if (haveToInsert){
                tempCartList.add(Cart(foodTitle!!, foodVariant, variantPrice, foodQuantity, foodPrice, totalUnitPrice, addonsPrice, addonsList,""))
            }
            sharedPref.writeSharedCartList(tempCartList)
            setCartRecyclerAdapter()
            dialog.dismiss()
        }


        // add multiple button click
        fdBinding.addMultiBtn.setOnClickListener {
            /*var haveToInsert = true
            var checkCounter = 0*/
            var addonsPrice = 0.0
            tempCartList.clear()
            totalUnitPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodPrice = variantPrice * foodQuantity

            if (sharedPref.readSharedCartList() != null){
                tempCartList = sharedPref.readSharedCartList()!!.toMutableList()
            }
            //val tempAddonList = mutableListOf<String>()
            if (addonsList.size > 0){
                for (i in addonsList.indices){
                    addonsPrice += addonsList[i].adnPrc
                }
            }
            tempCartList.add(Cart(foodTitle!!, foodVariant, variantPrice, foodQuantity, foodPrice, totalUnitPrice, addonsPrice, addonsList,""))
            sharedPref.writeSharedCartList(tempCartList)
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
                    grandTotal += cartList[i].tUPrc
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
}