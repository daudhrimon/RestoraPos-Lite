package com.bdtask.restoraposlite.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ScrollView
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.adapters.OngoingAdapter
import com.bdtask.restoraposlite.dialogs.PaymentDialog
import com.bdtask.restoraposlite.dialogs.TokenPrintDialog
import com.bdtask.restoraposlite.interfaces.OngoingClickListener
import com.bdtask.restoraposlite.interfaces.TokenClickListener
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.models.Payments
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.FragmentOngoingBinding
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class OngoingFragment : Fragment(), OngoingClickListener, TokenClickListener {
    private var _binding: FragmentOngoingBinding? = null
    private val oBinding get() = _binding!!
    private val sharedPref = SharedPref
    private var oPos = -1
    private var ongList = mutableListOf<Order>()
    private var selectedItem = -1
    private var foodCount = 0

    companion object {
        var clickedList = arrayListOf<Int>()
        var multiSelect = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOngoingBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        sharedPref.init(requireContext())
        sharedPref.writeEMode(0)


        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getOngoing(0).observe(viewLifecycleOwner, Observer {
            ongList.clear()
            ongList = it.toMutableList()
            clickedList.clear()
            oBinding.scrollView.visibility = View.GONE
            oBinding.tickBtn.visibility = View.GONE
            oBinding.header.text = "Ongoing Order"
            oBinding.searchBtn.visibility = View.VISIBLE

            setOnGoingAdapter()

        })


        oBinding.back.setOnClickListener {
            if (multiSelect) {
                disableMultiSelect()
            } else {
                findNavController().popBackStack()
            }
        }


        oBinding.searchBtn.setOnClickListener {
            if (oBinding.searchEt.visibility == View.GONE) {
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


        oBinding.searchEt.doOnTextChanged { text, start, before, count ->

            val srsList = mutableListOf<Order>()

            if (text.toString().isNotEmpty()) {

                srsList.clear()

                for (i in ongList.indices) {

                    if (ongList[i].id.toString()
                            .contains(text.toString()) || ongList[i].token.contains(text.toString()) || ongList[i].orderInfo.wtr.lowercase()
                            .contains(
                                text.toString().lowercase()
                            ) || ongList[i].orderInfo.tbl.lowercase().contains(
                            text.toString().lowercase()
                        ) || ongList[i].orderInfo.customerInfo.csNm.lowercase()
                            .contains(text.toString().lowercase())
                    ) {

                        srsList.add(ongList[i])
                    }

                    if (srsList.isNotEmpty()) {
                        oBinding.emptyOrder.visibility = View.GONE
                        oBinding.ongRecycler.visibility = View.VISIBLE
                        oBinding.ongRecycler.adapter =
                            OngoingAdapter(requireContext(), srsList, this)
                    } else {
                        oBinding.ongRecycler.visibility = View.GONE
                        oBinding.emptyOrder.visibility = View.VISIBLE
                    }
                }
            } else {

                setOnGoingAdapter()
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
            val dialog = PaymentDialog(requireContext(),Gson().toJson(ongList[oPos]),lifecycleScope)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((9 * width) / 10, (14 * height) / 15)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



        oBinding.editBtn.setOnClickListener {
            sharedPref.writeEMode(1)
            sharedPref.writeOrder(ongList[oPos])
            findNavController().navigate(R.id.ongFrag2homeFrag)
        }


        ////////////////////////////////////////////////////////////////////////////////////////////

        oBinding.tokenBtn.setOnClickListener {
            TokenPrintDialog(
                requireContext(),
                ongList[oPos].token,
                ongList[oPos].id,
                ongList[oPos].cart,
                ongList[oPos].orderInfo,
                this
            ).show()
        }


        oBinding.cancelBtn.setOnClickListener {
            val order = Order(
                ongList[oPos].id,
                2,
                0,
                0,
                ongList[oPos].date,
                ongList[oPos].token,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                sharedPref.readOperator() ?: "",
                ongList[oPos].orderInfo,
                ongList[oPos].cart,
                emptyList<Payments>().toMutableList()
            )

            if (selectedItem == 1) {
                clickedList.clear()

                lifecycleScope.launch(Dispatchers.IO) {

                    AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().updateOrder(order)

                    withContext(Dispatchers.Main) {

                        Toasty.success(
                            requireContext(), "Selected Order Cancelled", Toasty.LENGTH_SHORT, true
                        ).show()
                    }
                }

            } else {
                for (i in clickedList.indices) {

                    val pos = clickedList[i]

                    val order = Order(
                        ongList[pos].id,
                        2,
                        0,
                        0,
                        ongList[pos].date,
                        ongList[pos].token,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        sharedPref.readOperator() ?: "",
                        ongList[pos].orderInfo,
                        ongList[pos].cart,
                        emptyList<Payments>().toMutableList()
                    )

                    lifecycleScope.launch(Dispatchers.IO) {

                        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().updateOrder(order)

                        withContext(Dispatchers.Main) {
                            val items = clickedList.size
                            if (i == items - 1) {
                                clickedList.clear()
                                Toasty.success(
                                    requireContext(),
                                    "$items Selected Orders Cancelled",
                                    Toasty.LENGTH_SHORT,
                                    true
                                ).show()
                            }
                        }
                    }
                }
            }
            if (multiSelect) {
                multiSelect = false
                oBinding.root.isFocusableInTouchMode = false
                oBinding.root.clearFocus()
            }
        }


        oBinding.splitBtn.setOnClickListener {
            Toasty.info(
                requireContext(),
                "Split Order is only available\nin Restora POS\nPremium Version",
                Toasty.LENGTH_LONG,
                true
            ).show()
            /*sharedPref.writeOrder(ongList[oPos])
            val dialog = SplitOrderDialog(requireContext(), sharedPref, foodCount)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val win = dialog.window
            win!!.setLayout((14 * width)/15,(24 * height)/25)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
        }


        oBinding.mergeBtn.setOnClickListener {
            Toasty.info(
                requireContext(),
                "Merge Order is only available\nin Restora POS\nPremium Version",
                Toasty.LENGTH_LONG,
                true
            ).show()
        }



        return oBinding.root
    }

    private fun setOnGoingAdapter() {
        if (ongList.isNotEmpty()) {
            oBinding.emptyOrder.visibility = View.GONE
            oBinding.ongRecycler.visibility = View.VISIBLE
            oBinding.ongRecycler.adapter = OngoingAdapter(requireContext(), ongList, this)
        } else {
            oBinding.ongRecycler.visibility = View.GONE
            oBinding.emptyOrder.visibility = View.VISIBLE
        }
    }

    override fun onTokenButtonsClick(tokenPrintDialog: TokenPrintDialog) {
        tokenPrintDialog.dismissWithAnimation()
    }

    // from here we can handle onGoing Fragment's Buttons Visible or Gone from Adapter Class

    override fun onGoingItemClick(position: Int, selectedItem: Int) {
        this.selectedItem = selectedItem
        foodCount = 0
        oPos = position

        //////////////////////////
        if (selectedItem == 1) {
            for (i in ongList[position].cart.indices) {
                foodCount += ongList[position].cart[i].quantity
            }
        } else {
            oBinding.splitBtn.visibility = View.GONE
        }

        //////////////////////////
        if (multiSelect) {
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
                    oBinding.splitBtn.visibility = View.VISIBLE
                } else {
                    oBinding.splitBtn.visibility = View.GONE
                }
                oBinding.scrollView.visibility = View.VISIBLE
                oBinding.mergeBtn.visibility = View.GONE
                oBinding.cancelBtn.visibility = View.VISIBLE
                //oBinding.duePosBtn.visibility = View.VISIBLE
                oBinding.tokenBtn.visibility = View.VISIBLE
                oBinding.editBtn.visibility = View.VISIBLE
                oBinding.completeBtn.visibility = View.VISIBLE
                oBinding.scrollView.post {
                    oBinding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)
                }
            }

            else -> {
                oBinding.scrollView.visibility = View.VISIBLE
                //oBinding.duePosBtn.visibility = View.GONE
                oBinding.tokenBtn.visibility = View.GONE
                oBinding.editBtn.visibility = View.GONE
                oBinding.completeBtn.visibility = View.GONE
                oBinding.cancelBtn.visibility = View.VISIBLE
                oBinding.mergeBtn.visibility = View.VISIBLE
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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
