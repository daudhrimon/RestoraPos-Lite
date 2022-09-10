package com.bdtask.restoraposroomdbtab.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter
import com.bdtask.restoraposroomdbtab.Dialog.CPaymentDialog
import com.bdtask.restoraposroomdbtab.Dialog.InvoiceViewDialog
import com.bdtask.restoraposroomdbtab.Dialog.TokenDialog
import com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener
import com.bdtask.restoraposroomdbtab.Interface.TokenClickListener
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.database
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.Model.Pay
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class OngoingFragment : Fragment(), OngoingClickListener, TokenClickListener {
    private lateinit var oBinding: FragmentOngoingBinding
    private val sharedPref = SharedPref
    private var oPos = -1
    private var ongList = mutableListOf<Order>()
    private var selectedItem = -1
    private var foodCount = 0
    companion object {
        var clickedList = arrayListOf<Int>()
        var multiSelect = false
        var cusInfoList = mutableListOf<CsInf>()
        var cusNameList = mutableListOf<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        oBinding = FragmentOngoingBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())
        sharedPref.writeEMode(0)


        database.orderDao().getOngoing(0).observe(viewLifecycleOwner, Observer {
            ongList.clear()
            ongList = it.toMutableList()

            clickedList.clear()
            oBinding.scrollView.visibility = View.GONE
            oBinding.tickBtn.visibility = View.GONE
            oBinding.header.text = "Ongoing Order"
            oBinding.searchBtn.visibility = View.VISIBLE

            if (ongList.isNotEmpty()){
                oBinding.emptyOrder.visibility = View.GONE
                oBinding.ongRecycler.visibility = View.VISIBLE
                oBinding.ongRecycler.adapter = OngoingAdapter(requireContext(), ongList,this)
            } else {
                oBinding.ongRecycler.visibility = View.GONE
                oBinding.emptyOrder.visibility = View.VISIBLE
            }
            Log.wtf("A bodda iam alive","But aske amar mon valo nei : "+ ongList.size)
        })




        oBinding.back.setOnClickListener {
            if (multiSelect){
                disableMultiSelect()
            } else {
                findNavController().popBackStack()
            }
        }



        oBinding.searchBtn.setOnClickListener {
            if (oBinding.searchEt.visibility == View.GONE){
                oBinding.header.visibility = View.GONE
                oBinding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(oBinding.searchEt)
                oBinding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                oBinding.searchEt.visibility = View.GONE
                oBinding.header.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), oBinding.root)
                oBinding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }



        oBinding.root.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && multiSelect) {
                disableMultiSelect()
                return@OnKeyListener true
            }
            false
        })



        oBinding.tickBtn.setOnClickListener {
            disableMultiSelect()
        }



        ////////////////////////////////////////////////////////////////////////////////////////////


        oBinding.completeBtn.setOnClickListener {
            sharedPref.writeOrder(ongList[oPos])
            val dialog = CPaymentDialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((9 * width)/10,(14 * height)/15)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        oBinding.cancelBtn.setOnClickListener {
            val order = Order(ongList[oPos].id,2,0,0,
                ongList[oPos].dat, ongList[oPos].tkn,0.0, 0.0,0.0,0.0,
                ongList[oPos].odrInf, ongList[oPos].cart, emptyList<Pay>().toMutableList())

            if (selectedItem == 1){
                clickedList.clear()

                GlobalScope.launch(Dispatchers.IO) {

                    database.orderDao().updateOrder(order)

                    withContext(Dispatchers.Main){

                        Toasty.success(requireContext(),"Selected Order Cancelled",Toasty.LENGTH_SHORT,true).show()
                    }
                }

            } else {
                for (i in clickedList.indices){

                    val pos = clickedList[i]

                    val order = Order(ongList[pos].id,2,0,0,
                        ongList[pos].dat, ongList[pos].tkn,0.0, 0.0,0.0, 0.0,
                        ongList[pos].odrInf, ongList[pos].cart,emptyList<Pay>().toMutableList())

                    GlobalScope.launch(Dispatchers.IO) {

                        database.orderDao().updateOrder(order)

                        withContext(Dispatchers.Main){
                            val items = clickedList.size
                            if (i == items-1){
                                clickedList.clear()
                                Toasty.success(requireContext(),"$items Selected Orders Cancelled",Toasty.LENGTH_SHORT,true).show()
                            }
                        }
                    }
                }
            }
            if (multiSelect){
                multiSelect = false
                oBinding.root.isFocusableInTouchMode = false
                oBinding.root.clearFocus()
            }
        }


        /*oBinding.splitBtn.setOnClickListener {
            sharedPref.writeSharedOrder(ongList[oPos])
            val dialog = SplitOrderDialog(requireContext(), sharedPref, foodCount)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }*/

        oBinding.editBtn.setOnClickListener {
            sharedPref.writeEMode(1)
            sharedPref.writeOrder(ongList[oPos])
            findNavController().navigate(R.id.ongFrag2homeFrag)
        }

        ////////////////////////////////////////////////////////////////////////////////////////////


        // getting  customer name live and setting to spinner live
        database.customerDao().getAllCustomer().observe(viewLifecycleOwner, Observer{
            cusNameList.clear()
            for (i in it.indices){
                cusNameList.add(it[i].nm)
                cusInfoList.add(CsInf(it[i].nm, it[i].adrs, it[i].mbl))
            }
        })



        oBinding.tokenBtn.setOnClickListener {
            TokenDialog(requireContext(),ongList[oPos].tkn,ongList[oPos].id,
                ongList[oPos].cart,ongList[oPos].odrInf,this).show()
        }



        oBinding.duePosBtn.setOnClickListener {
            sharedPref.writeOrder(ongList[oPos])
            val dialog = InvoiceViewDialog(requireContext(),0)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        return oBinding.root
    }

    override fun onTokenButtonsClick(tokenDialog: TokenDialog) {
        tokenDialog.dismissWithAnimation()
    }

    // from here we can handle onGoing Fragment's Buttons Visible or Gone from Adapter Class

    override fun onGoingItemClick(position: Int, selectedItem: Int) {
        this.selectedItem = selectedItem
        foodCount = 0
        oPos = position

        //////////////////////////
        if (selectedItem == 1) {
            for (i in ongList[position].cart.indices) {
                foodCount += ongList[position].cart[i].fQnty
            }
        } else {
            ///oBinding.splitBtn.visibility = View.GONE
        }

        //////////////////////////
        if (multiSelect){
            oBinding.searchBtn.visibility = View.GONE
            oBinding.tickBtn.visibility = View.VISIBLE
            oBinding.header.text = "${clickedList.size} / ${ongList.size} Selected"
            oBinding.root.isFocusableInTouchMode = true
            oBinding.root.requestFocus()
        }

        //////////////////////////
        when (selectedItem) {
            0 -> {
                oBinding.scrollView.visibility = View.GONE
            }

            1 -> {
                if (foodCount > 1) {
                    //oBinding.splitBtn.visibility = View.VISIBLE
                } else {
                    //oBinding.splitBtn.visibility = View.GONE
                }
                oBinding.scrollView.visibility = View.VISIBLE
                //oBinding.mergeBtn.visibility = View.GONE
                oBinding.cancelBtn.visibility = View.VISIBLE
                oBinding.duePosBtn.visibility = View.VISIBLE
                oBinding.tokenBtn.visibility = View.VISIBLE
                oBinding.editBtn.visibility = View.VISIBLE
                oBinding.completeBtn.visibility = View.VISIBLE
                oBinding.scrollView.post {
                    oBinding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)
                }
            }

            else -> {
                oBinding.scrollView.visibility = View.VISIBLE
                oBinding.duePosBtn.visibility = View.GONE
                oBinding.tokenBtn.visibility = View.GONE
                oBinding.editBtn.visibility = View.GONE
                oBinding.completeBtn.visibility = View.GONE
                oBinding.cancelBtn.visibility = View.VISIBLE
               // oBinding.mergeBtn.visibility = View.VISIBLE
            }
        }
        oBinding.ongRecycler.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun disableMultiSelect() {
        oBinding.scrollView.visibility = View.GONE
        multiSelect = false
        clickedList.clear()
        oBinding.tickBtn.visibility = View.GONE
        oBinding.header.text = "Ongoing Order"
        oBinding.searchBtn.visibility = View.VISIBLE
        oBinding.ongRecycler.adapter?.notifyDataSetChanged()
        oBinding.root.isFocusableInTouchMode = false
        oBinding.root.clearFocus()
    }
}
