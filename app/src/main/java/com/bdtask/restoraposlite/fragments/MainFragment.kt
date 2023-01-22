package com.bdtask.restoraposlite.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.adapters.*
import com.bdtask.restoraposlite.dialogs.*
import com.bdtask.restoraposlite.interfaces.CartClickListener
import com.bdtask.restoraposlite.interfaces.FoodClickListener
import com.bdtask.restoraposlite.interfaces.TokenClickListener
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.models.*
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Food
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogFoodClickBinding
import com.bdtask.restoraposlite.databinding.DialogFoodLongClickBinding
import com.bdtask.restoraposlite.databinding.FragmentMainBinding
import com.bdtask.restoraposlite.room.entity.Customer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.lang.Exception

class MainFragment : Fragment(), FoodClickListener, CartClickListener, TokenClickListener {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private var categoryList = mutableListOf<String>()
    private var variantNameList = mutableListOf<String>()
    private var grandTotal = 0.0
    private var foodVariant = ""
    private var variantPrice = 0.0
    private var foodQuantity = 1
    private var foodPrice = 0.0
    private var totalUnitPrice = 0.0
    private var addonsList = mutableListOf<Addon>()
    private var cartList = mutableListOf<Cart>()
    private var sharedPref = SharedPref
    private var token = ""
    private var eMode = 0
    private var orderUp: Order? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        eMode = sharedPref.readEMOde() ?: 0

        checkEditMode()

        // getting and setting category Recycler
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getCategories().observe(viewLifecycleOwner, Observer {
            categoryList.clear()
            categoryList = it.toMutableList()

            if (categoryList.size > 0) {
                categoryList.add(0, "All Category")
                mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            } else {
                categoryList.add(0, "There is No Food Category Found")
                mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
            }

            mBinding.viewPager2.adapter = CategoryAdapter(
                requireContext(), categoryList, mBinding.searchEt, this
            )

            TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager2) { tab, position ->
                tab.text = categoryList[position]
            }.attach()
        })


        // setting cart Recycler Adapter
        setCartRecyclerAdapter()


        // menu Button Click
        mBinding.menuBtn.setOnClickListener {
            drawerLayout.open()
        }


        // PLUS Button Click
        mBinding.orderInfo.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2orderInfoFrag)
        }


        // OnGoing Button Click
        mBinding.ongoingTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2ongoingFrag)
        }


        // Today Button Click
        mBinding.todayTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2todayFrag)
        }


        // All Order Button Click
        mBinding.completedTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2comFrag)
        }


        // Cancelled Order Click
        mBinding.canceledTv.setOnClickListener {
            findNavController().navigate(R.id.homeFrag2canFrag)
        }


        // Calculator BUTTON Click
        mBinding.calculatorLay.setOnClickListener {
            val dialog: Dialog = CalculatorDialog(requireContext())
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((6 * width) / 7, (6 * height) / 7)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }


        // cart Delete Click
        mBinding.deleteBtn.setOnClickListener {
            if (sharedPref.readCart() == null || sharedPref.readCart()!!.isEmpty()) {
                Toasty.info(requireContext(), "Nothing To Delete !", Toast.LENGTH_SHORT, true)
                    .show()
            } else {
                sharedPref.writeCart(emptyList<Cart>().toMutableList())
                setCartRecyclerAdapter()
                Toasty.success(requireContext(), "Delete Successful", Toast.LENGTH_SHORT, true)
                    .show()
            }
        }


        // quick order Click Handler
        mBinding.quickOrder.setOnClickListener {
            if (eMode == 0) {

                quickOrderClick()

            } else {
                eMode = 0
                checkEditMode()
                sharedPref.writeCart(emptyList<Cart>().toMutableList())
                sharedPref.writeOrderInfo(OrderInfo(CustomerInfo("", "", ""), "", "", "", "", ""))
                sharedPref.writeEMode(0)
                setCartRecyclerAdapter()
            }
        }


        // placeOrder Click Handler
        mBinding.placeOrder.setOnClickListener {
            placeOrderClick()
        }




        return mBinding.root
    }


    private fun checkEditMode() {
        if (eMode == 1) {
            orderUp = sharedPref.readOrder()

            mBinding.topBtnLay.visibility = View.GONE
            mBinding.deleteCard.visibility = View.GONE
            mBinding.quickOrder.setBackgroundResource(R.drawable.selector_close)
            mBinding.quickTv.text = "Cancel Edit"
            mBinding.placeTv.text = "Update Order"

            if (orderUp != null) {
                if (orderUp!!.cart.isNotEmpty()) {
                    sharedPref.writeCart(orderUp!!.cart)
                    sharedPref.writeOrderInfo(orderUp!!.orderInfo)
                }
            }
        } else {
            mBinding.topBtnLay.visibility = View.VISIBLE
            mBinding.deleteCard.visibility = View.VISIBLE
            mBinding.quickOrder.setBackgroundResource(R.drawable.selector_ongoing)
            mBinding.quickTv.text = "Quick Order"
            mBinding.placeTv.text = "Place Order"
        }
    }


    private fun quickOrderClick() {

        var tempCartList = mutableListOf<Cart>()
        var orderInfo = OrderInfo(CustomerInfo("", "", ""), "", "", "", "", "")

        tempCartList = sharedPref.readCart() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readOrderInfo() != null) {
            orderInfo = sharedPref.readOrderInfo()!!
        }

        if (tempCartList.isNotEmpty()) {
            if (orderInfo.customerInfo.csNm.isNotEmpty() && orderInfo.csTyp.isNotEmpty()) {

                val order = Order(
                    0,
                    1,
                    0,
                    0,
                    Util.getDate().toString(),
                    "",
                    0.0,
                    sharedPref.readVat() ?: 0.0,
                    0.0,
                    sharedPref.readCharge() ?: 0.0,
                    0.0,
                    0.0,
                    0.0,
                    sharedPref.readOperator() ?: "",
                    orderInfo,
                    tempCartList,
                    emptyList<Payments>().toMutableList()
                )

                sharedPref.writeCart(emptyList<Cart>().toMutableList())

                setCartRecyclerAdapter()

                try {
                    lifecycleScope.launch(Dispatchers.IO) {

                        val orderId = AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertOrder(order)

                        withContext(Dispatchers.Main) {

                            if (orderId != null && orderId.toString().isNotEmpty()) {

                                Toasty.success(
                                    requireContext(),
                                    "Order Placed $orderId Successfully",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toasty.success(requireContext(), e.message.toString(), Toast.LENGTH_SHORT, true)
                        .show()
                }

                // InVoice View Dialog
                val dialog = PaymentDialog(requireContext(), Gson().toJson(order), lifecycleScope)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.show()
                val width = resources.displayMetrics.widthPixels
                val height = resources.displayMetrics.heightPixels
                val win = dialog.window
                win!!.setLayout((9 * width) / 10, (14 * height) / 15)
                win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            } else {
                mBinding.focusLottie.visibility = View.VISIBLE
                Toasty.error(requireActivity(), "Set Order Info First !", Toast.LENGTH_SHORT, true)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    mBinding.focusLottie.visibility = View.GONE
                }, 5000)
            }
        } else {
            Toasty.error(requireActivity(), "Add Food To Cart First !", Toast.LENGTH_SHORT, true)
                .show()
        }
    }


    // place order ClickHandler
    private fun placeOrderClick() {
        var tempCartList = mutableListOf<Cart>()
        var orderInfo = OrderInfo(CustomerInfo("", "", ""), "", "", "", "", "")

        tempCartList = sharedPref.readCart() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readOrderInfo() != null) {
            orderInfo = sharedPref.readOrderInfo()!!
        }


        if (tempCartList.isNotEmpty()) {
            if (orderInfo.customerInfo.csNm.isNotEmpty() && orderInfo.csTyp.isNotEmpty()) {

                if (eMode == 0) {

                    token = Util.getToken(sharedPref)

                    val order = Order(
                        0,
                        0,
                        0,
                        0,
                        Util.getDate().toString(),
                        token,
                        0.0,
                        sharedPref.readVat() ?: 0.0,
                        0.0,
                        sharedPref.readCharge() ?: 0.0,
                        0.0,
                        0.0,
                        0.0,
                        sharedPref.readOperator() ?: "",
                        orderInfo,
                        tempCartList,
                        emptyList<Payments>().toMutableList()
                    )

                    try {
                        lifecycleScope.launch(Dispatchers.IO) {

                            val orderId = AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertOrder(order)

                            withContext(Dispatchers.Main) {

                                if (orderId != null && orderId.toString().isNotEmpty()) {

                                    Toasty.success(
                                        requireContext(),
                                        "Order Placed $orderId Successfully",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()

                                    // asking for print token
                                    printToken(orderId)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Toasty.success(
                            requireContext(), e.message.toString(), Toast.LENGTH_SHORT, true
                        ).show()
                    }

                } else {

                    // EDIT Order

                    if (orderUp != null) {
                        orderUp!!.orderInfo = orderInfo
                        orderUp!!.cart = tempCartList
                        orderUp!!.vat = sharedPref.readVat() ?: 0.0
                        orderUp!!.charge = sharedPref.readCharge() ?: 0.0

                        try {
                            lifecycleScope.launch(Dispatchers.IO) {

                                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().updateOrder(orderUp!!)

                                withContext(Dispatchers.Main) {

                                    Toasty.success(
                                        requireContext(),
                                        "Update Order ${orderUp!!.id} Successfully",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()

                                    // asking for print token
                                    printToken(orderUp!!.id)

                                    orderUp = null

                                    eMode = 0
                                    checkEditMode()
                                    sharedPref.writeEMode(0)
                                }
                            }
                        } catch (e: Exception) {
                            Toasty.success(
                                requireContext(), e.message.toString(), Toast.LENGTH_SHORT, true
                            ).show()
                            eMode = 0
                            checkEditMode()
                            sharedPref.writeCart(emptyList<Cart>().toMutableList())
                            sharedPref.writeOrderInfo(
                                OrderInfo(
                                    CustomerInfo("", "", ""),
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                )
                            )
                            sharedPref.writeEMode(0)
                            setCartRecyclerAdapter()
                        }
                    }
                }
            } else {
                mBinding.focusLottie.visibility = View.VISIBLE
                Toasty.error(requireActivity(), "Set Order Info First !", Toast.LENGTH_SHORT, true)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    mBinding.focusLottie.visibility = View.GONE
                }, 5000)
            }
        } else {
            Toasty.error(requireActivity(), "Add Food To Cart First !", Toast.LENGTH_SHORT, true)
                .show()
        }
    }


    // Token dialog Click Listener
    override fun onTokenButtonsClick(tokenPrintDialog: TokenPrintDialog) {
        tokenPrintDialog.dismissWithAnimation()
        setCartRecyclerAdapter()
    }


    // ask for print Token
    private fun printToken(orderId: Long) {
        var tempCartList = mutableListOf<Cart>()
        var orderInfo = OrderInfo(CustomerInfo("", "", ""), "", "", "", "", "")

        tempCartList = sharedPref.readCart() ?: emptyList<Cart>().toMutableList()

        if (sharedPref.readOrderInfo() != null) {
            orderInfo = sharedPref.readOrderInfo()!!
        }

        TokenPrintDialog(requireContext(), token, orderId, tempCartList, orderInfo, this).show()

        sharedPref.writeCart(emptyList<Cart>().toMutableList())
    }


    // show alert Dialog BasedOn Food Item Click
    override fun onFoodClick(
        foodId: Long?, foodTitle: String?, variantsList: List<Variants>, addonsList: List<Addons>
    ) {
        val dialog = Dialog(requireContext())
        val fdBinding = DialogFoodClickBinding.bind(
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_food_click, null)
        )
        dialog.setContentView(fdBinding.root)

        foodVariant = ""
        variantPrice = 0.0
        foodQuantity = 1
        foodPrice = 0.0
        totalUnitPrice = 0.0
        this.addonsList.clear()
        val newAddonList = mutableListOf<Addon>()

        fdBinding.dcFoodName.text = foodTitle

        variantNameList.clear()
        for (i in variantsList.indices) {
            variantNameList.add(variantsList[i].variant)
        }

        fdBinding.dcVariantSpinner.adapter = context?.let {
            ArrayAdapter(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                variantNameList
            )
        }

        fdBinding.dcVariantSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long
                ) {

                    foodVariant = fdBinding.dcVariantSpinner.selectedItem.toString()

                    for (i in variantsList.indices) {
                        if (variantsList[i].variant == foodVariant) {

                            variantPrice = variantsList[i].price

                            foodQuantity = 1
                            fdBinding.dcQuantity.text = foodQuantity.toString()

                            foodPrice = variantPrice

                            totalUnitPrice = variantPrice
                            fdBinding.dcFoodPrice.text = totalUnitPrice.toString()
                            this@MainFragment.addonsList.clear()
                            fdBinding.addonsRecycler.adapter?.notifyDataSetChanged()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }

        if (addonsList.isNotEmpty()) {
            fdBinding.addonsRecycler.visibility = View.VISIBLE
            fdBinding.addonsRecycler.adapter =
                AddonsAdapter(requireContext(), addonsList, fdBinding, this.addonsList)
        }

        fdBinding.dcCrossBtn.setOnClickListener {
            dialog.dismiss()
        }

        fdBinding.dcPlusBtn.setOnClickListener {
            var foodPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = fdBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity < 99) {

                foodQuantity += 1
                fdBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice += variantPrice
                fdBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }

        fdBinding.dcMinusBtn.setOnClickListener {
            var foodPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
            foodQuantity = fdBinding.dcQuantity.text.toString().toInt()

            if (foodQuantity > 1) {

                foodQuantity -= 1
                fdBinding.dcQuantity.text = foodQuantity.toString()

                foodPrice -= variantPrice
                fdBinding.dcFoodPrice.text = foodPrice.toString()
            }
        }


        // add To Cart Button Click
        fdBinding.addToCartBtn.setOnClickListener {
            addToCartHandler(newAddonList, fdBinding, foodTitle, dialog)
        }


        // add multiple button click
        fdBinding.addMultiBtn.setOnClickListener {
            addToCartHandler(newAddonList, fdBinding, foodTitle, null)
        }


        dialog.show()
        dialog.setCancelable(false)
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun addToCartHandler(
        newAddonList: MutableList<Addon>,
        fdBinding: DialogFoodClickBinding,
        foodTitle: String?,
        dialog: Dialog?
    ) {
        var tempCartList = mutableListOf<Cart>()
        var haveToInsert = false
        var addonsPrice = 0.0
        newAddonList.clear()
        totalUnitPrice = fdBinding.dcFoodPrice.text.toString().toDouble()
        foodPrice = variantPrice * foodQuantity

        if (sharedPref.readCart() != null) {
            tempCartList = sharedPref.readCart()!!.toMutableList()
        }

        //fake old addonList
        val oldAddons = arrayListOf<String>()
        if (this.addonsList.size > 0) {
            for (h in this.addonsList.indices) {
                addonsPrice += this.addonsList[h].price
                oldAddons.add(addonsList[h].name)
            }
        }

        if (tempCartList.size > 0) {

            for (i in tempCartList.indices) {

                if (tempCartList[i].title == foodTitle && tempCartList[i].variant == foodVariant) {

                    //fake new addonList
                    val newAddons = arrayListOf<String>()
                    for (n in tempCartList[i].addon.indices) {
                        newAddons.add(tempCartList[i].addon[n].name)
                    }

                    if (Gson().toJson(newAddons).contains(Gson().toJson(oldAddons))) {
                        //marge Addons by Name
                        val addonListByName = this.addonsList.associateBy { it.name }
                        tempCartList[i].addon.forEach { tempAdn ->
                            addonListByName[tempAdn.name]?.let { homeAdn ->
                                tempAdn.price += homeAdn.price
                                tempAdn.quantity += homeAdn.quantity
                            }
                        }
                        haveToInsert = false
                        tempCartList[i].quantity += foodQuantity
                        tempCartList[i].price += foodPrice
                        tempCartList[i].subTotal += totalUnitPrice
                        tempCartList[i].addonTotal += addonsPrice
                        break
                    } else {
                        haveToInsert = true
                    }
                } else {
                    haveToInsert = true
                }
            }

        } else {
            haveToInsert = true
        }

        if (haveToInsert) {
            tempCartList.add(
                Cart(
                    foodTitle!!,
                    foodVariant,
                    variantPrice,
                    foodQuantity,
                    foodPrice,
                    totalUnitPrice,
                    addonsPrice,
                    this.addonsList,
                    ""
                )
            )
        }
        sharedPref.writeCart(tempCartList)
        setCartRecyclerAdapter()
        dialog?.dismiss()
    }


    override fun onFoodLongClick(food: Food, foodClickListener: FoodClickListener) {
        val dialog = Dialog(requireContext())
        val binding = DialogFoodLongClickBinding.bind(
            layoutInflater.inflate(
                R.layout.dialog_food_long_click, null
            )
        )
        dialog.setContentView(binding.root)
        binding.crossBtn.visibility = View.VISIBLE

        binding.crossBtn.setOnClickListener { dialog.dismiss() }

        binding.dltBtn.setOnClickListener {
            dialog.dismiss()
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Food Delete Alert !")
            alert.setMessage("Are you sure ? You want to Delete this Food ?")

            alert.setNegativeButton("No", DialogInterface.OnClickListener { dial, which ->
                dial.dismiss()
            })

            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dial, which ->

                lifecycleScope.launch(Dispatchers.IO) {

                    AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().deleteFood(food)

                    withContext(Dispatchers.Main) {
                        mBinding.viewPager2.postDelayed({
                            dial.dismiss()
                            Toasty.success(
                                requireContext(),
                                "Food Deleted Successfully",
                                Toasty.LENGTH_SHORT,
                                true
                            ).show()
                            mBinding.viewPager2.adapter = CategoryAdapter(
                                requireContext(), categoryList, mBinding.searchEt, foodClickListener
                            )
                        }, 1000)
                    }
                }
            })

            alert.show()
        }

        binding.editBtn.setOnClickListener {
            dialog.dismiss()
            val bundle = bundleOf("Food" to Gson().toJson(food))
            findNavController().navigate(R.id.homeFrag2foodFrag, bundle)
        }

        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    // setting cart Recycler Adapter
    private fun setCartRecyclerAdapter() {
        cartList.clear()
        cartList = sharedPref.readCart() ?: emptyList<Cart>().toMutableList()

        if (cartList.isNotEmpty()) {
            mBinding.cartRecyclerLay.visibility = View.VISIBLE
            mBinding.cartRecycler.visibility = View.VISIBLE
            mBinding.cartRecycler.adapter = CartAdapter(requireContext(), cartList, this)

            grandTotal = 0.0
            for (i in cartList.indices) {
                grandTotal += cartList[i].subTotal
            }
            mBinding.grandTotalTv.text = ": $grandTotal $appCurrency"
        } else {
            mBinding.grandTotalTv.text = ": 0.0 $appCurrency"
            mBinding.cartRecyclerLay.visibility = View.GONE
            mBinding.cartRecycler.visibility = View.GONE
        }
    }


    // set CardRecycler on Cart Item Delete
    override fun onCartReload() {
        setCartRecyclerAdapter()
    }


    override fun onResume() {
        super.onResume()

        if ((sharedPref.readWelcome() ?: 0) == 0) {

            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao()
                    .insertCustomer(
                        Customer(-1, "Walk In", "Not provided", "Not provided", "Not provided")
                    )

                withContext(Dispatchers.Main) {

                    sharedPref.writeWelcome(1)

                    findNavController().navigate(R.id.homeFrag2foodFrag)
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}