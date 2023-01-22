package com.bdtask.restoraposlite.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.dialogs.AddCustomerDialog
import com.bdtask.restoraposlite.dialogs.BtmSItemRecyclerDialog
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.models.CustomerInfo
import com.bdtask.restoraposlite.models.OrderInfo
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Company
import com.bdtask.restoraposlite.room.entity.Customer
import com.bdtask.restoraposlite.room.entity.Table
import com.bdtask.restoraposlite.room.entity.Waiter
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposlite.databinding.FragmentOrderInfoBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class OrderInfoFragment : Fragment() {
    private var _binding: FragmentOrderInfoBinding? = null
    private val oBinding get() = _binding!!
    private var cusNameList = mutableListOf<String>()
    private var cusInfoList = mutableListOf<CustomerInfo>()
    private var deliveryCompanyList = mutableListOf<Company>()
    private var deliveryCompanySpnrList = mutableListOf<String>()
    private var cusTypeList = arrayListOf<String>()
    private var waiterList = mutableListOf<Waiter>()
    private var waiterSpnrList = mutableListOf<String>()
    private var tableList = mutableListOf<Table>()
    private var tableSpnrList = mutableListOf<String>()
    private lateinit var selectedCustomerInfo: CustomerInfo
    private var selectedCustomerType = ""
    private var selectedWaiter = ""
    private var selectedTable = ""
    private var selectedDeliveryCompany = ""
    private lateinit var orderInfo: OrderInfo
    private var sharedPref = SharedPref
    private var orderInfo2: OrderInfo? = null
    private var btmDialog: BtmSItemRecyclerDialog? = null

    companion object {
        var state = ""
    }

    private var customerList = mutableListOf<Customer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderInfoBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        sharedPref.init(requireContext())


        // get Shared/Saved Customer Info
        orderInfo2 = sharedPref.readOrderInfo()



        oBinding.ocfBack.setOnClickListener {
            findNavController().popBackStack()
        }


        // getting  customer name live and setting to spinner live
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getAllCustomer().observe(viewLifecycleOwner, Observer {
            customerList.clear()
            cusNameList.clear()
            cusInfoList.clear()
            customerList = it.toMutableList()
            for (i in it.indices) {
                cusNameList.add(it[i].name)
                cusInfoList.add(CustomerInfo(it[i].name, it[i].address, it[i].mobile))
            }
            oBinding.cusNameSpnr.adapter =
                ArrayAdapter(requireContext(), R.layout.custom_spinner_layout, cusNameList)

            if (orderInfo2 != null) {
                if (orderInfo2!!.customerInfo.csNm.isNotEmpty()) {
                    for (i in cusInfoList.indices) {
                        if (cusInfoList[i].csNm == orderInfo2!!.customerInfo.csNm && cusInfoList[i].csAdrs == orderInfo2!!.customerInfo.csAdrs && cusInfoList[i].mbl == orderInfo2!!.customerInfo.mbl) {

                            oBinding.cusNameSpnr.setSelection(i)
                        }
                    }
                }
            }
        })


        // Customer Spinner
        oBinding.cusNameSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {

                val selectedItem = oBinding.cusNameSpnr.selectedItem.toString()

                for (i in cusInfoList.indices) {
                    if (cusInfoList[i].csNm == selectedItem && cusNameList[spnrPos] == cusInfoList[spnrPos].csNm) {
                        selectedCustomerInfo =
                            CustomerInfo(cusInfoList[i].csNm, cusInfoList[i].csAdrs, cusInfoList[i].mbl)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {/**/
            }
        }


        // Customer Type Spinner Set
        cusTypeList = arrayListOf<String>("Walk In", "Take Way", "Online Customer", "Third Party")
        oBinding.cusTypeSpnr.adapter = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            cusTypeList
        )


        // customer Type Spinner select Listener
        oBinding.cusTypeSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCustomerType = oBinding.cusTypeSpnr.selectedItem.toString()
                if (selectedCustomerType.equals("Walk In")) {
                    oBinding.waiterLay.visibility = View.VISIBLE
                    oBinding.tableLay.visibility = View.VISIBLE
                    oBinding.deliveryCompanyLay.visibility = View.GONE
                    oBinding.orderIdLay.visibility = View.GONE
                } else if (selectedCustomerType.equals("Online Customer") || selectedCustomerType.equals(
                        "Take Way"
                    )
                ) {
                    oBinding.waiterLay.visibility = View.VISIBLE
                    oBinding.tableLay.visibility = View.GONE
                    oBinding.deliveryCompanyLay.visibility = View.GONE
                    oBinding.orderIdLay.visibility = View.GONE
                } else {
                    oBinding.waiterLay.visibility = View.GONE
                    oBinding.tableLay.visibility = View.GONE
                    oBinding.deliveryCompanyLay.visibility = View.VISIBLE
                    oBinding.orderIdLay.visibility = View.VISIBLE
                    if (orderInfo2 != null) {
                        if (orderInfo2!!.odrIdTp.isNotEmpty()) {
                            oBinding.orderIdEt.setText(orderInfo2!!.odrIdTp)
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {/**/
            }
        }


        //getting waiter Live and setting to Waiter spinner Live
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getAllWaiter().observe(viewLifecycleOwner, Observer {
            waiterList.clear()
            waiterSpnrList.clear()
            waiterList = it.toMutableList()
            for (i in waiterList.indices) {
                waiterSpnrList.add(waiterList[i].name)
            }
            oBinding.waiterSpnr.adapter = ArrayAdapter(
                requireContext(),
                com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
                waiterSpnrList
            )

            if (btmDialog != null) {
                if (btmDialog!!.isShowing && state == "wtr") {
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(
                        requireContext(),
                        waiterList,
                        emptyList<Company>().toMutableList(),
                        emptyList<Table>().toMutableList(),
                        lifecycleScope
                    )
                    btmDialog?.show()
                }
            }

            if (orderInfo2 != null) {
                if (orderInfo2!!.wtr.isNotEmpty()) {
                    for (i in waiterSpnrList.indices) {
                        if (waiterSpnrList[i] == orderInfo2!!.wtr) {
                            oBinding.waiterSpnr.setSelection(i)
                        }
                    }
                }
            }
        })


        //Waiter Spinner
        oBinding.waiterSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedWaiter = oBinding.waiterSpnr.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {/**/
            }
        }


        // getting and setting Table Spinner
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getAllTable().observe(viewLifecycleOwner, Observer {
            tableList.clear()
            tableList = it.toMutableList()
            tableSpnrList.clear()
            for (i in tableList.indices) {
                tableSpnrList.add(tableList[i].name)
            }

            oBinding.tableSpnr.adapter = ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                tableSpnrList
            )

            if (btmDialog != null) {
                if (btmDialog!!.isShowing && state == "tbl") {
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(
                        requireContext(),
                        emptyList<Waiter>().toMutableList(),
                        emptyList<Company>().toMutableList(),
                        tableList,
                        lifecycleScope
                    )
                    btmDialog?.show()
                }
            }

            if (orderInfo2 != null) {
                if (orderInfo2!!.tbl.isNotEmpty()) {
                    for (i in tableSpnrList.indices) {
                        if (tableSpnrList[i] == orderInfo2!!.tbl) {
                            oBinding.tableSpnr.setSelection(i)
                        }
                    }
                }
            }
        })


        // table spinner selected listener
        oBinding.tableSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedTable = oBinding.tableSpnr.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {/**/
            }
        }


        //getting delivery Company Live and setting to spinner live
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getDeliveryCompany().observe(viewLifecycleOwner, Observer {
            deliveryCompanyList.clear()
            deliveryCompanyList.clear()
            deliveryCompanyList = it.toMutableList()
            for (i in deliveryCompanyList.indices) {
                deliveryCompanySpnrList.add(deliveryCompanyList[i].name)
            }
            oBinding.deliveryCompanySpnr.adapter = ArrayAdapter(
                requireContext(),
                com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
                deliveryCompanySpnrList
            )

            if (btmDialog != null) {
                if (btmDialog!!.isShowing && state == "dc") {
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(
                        requireContext(),
                        waiterList,
                        emptyList<Company>().toMutableList(),
                        emptyList<Table>().toMutableList(),
                        lifecycleScope
                    )
                    btmDialog?.show()
                }
            }

            if (orderInfo2 != null) {
                if (orderInfo2!!.dlvCo.isNotEmpty()) {
                    for (i in deliveryCompanySpnrList.indices) {
                        if (deliveryCompanySpnrList[i] == orderInfo2!!.dlvCo) {
                            oBinding.deliveryCompanySpnr.setSelection(i)
                        }
                    }
                }
            }
        })


        // delivery Company Spinner
        oBinding.deliveryCompanySpnr.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedDeliveryCompany = oBinding.deliveryCompanySpnr.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }





        oBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), oBinding.root) }

        oBinding.cusAddBtn.setOnClickListener {
            val dialog = AddCustomerDialog(requireContext(), lifecycleScope)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        oBinding.deliveryCmpnyAddBtn.setOnClickListener {
            deliveryCompanyAddBtnClick()
        }

        oBinding.deliveryCmpnyAddBtn.setOnLongClickListener {
            state = "dc"
            btmDialog = BtmSItemRecyclerDialog(
                requireContext(),
                emptyList<Waiter>().toMutableList(),
                deliveryCompanyList,
                emptyList<Table>().toMutableList(),
                lifecycleScope
            )
            btmDialog?.show()
            return@setOnLongClickListener true
        }

        oBinding.waiterAddBtn.setOnClickListener {
            waiterAddBtnClick()
        }

        oBinding.waiterAddBtn.setOnLongClickListener {
            state = "wtr"
            btmDialog = BtmSItemRecyclerDialog(
                requireContext(),
                waiterList,
                emptyList<Company>().toMutableList(),
                emptyList<Table>().toMutableList(),
                lifecycleScope
            )
            btmDialog?.show()
            return@setOnLongClickListener true
        }

        oBinding.tableAddBtn.setOnClickListener {
            tableAddBtnClick()
        }

        oBinding.tableAddBtn.setOnLongClickListener {
            state = "tbl"
            btmDialog = BtmSItemRecyclerDialog(
                requireContext(),
                emptyList<Waiter>().toMutableList(),
                emptyList<Company>().toMutableList(),
                tableList,
                lifecycleScope
            )
            btmDialog?.show()
            return@setOnLongClickListener true
        }

        oBinding.doneBtn.setOnClickListener {
            //findNavController().popBackStack()
            doneButtonClickHandler()
        }

        return oBinding.root
    }


    private fun doneButtonClickHandler() {
        if (cusInfoList.size == 0) {
            Toasty.error(requireContext(), "Customer's Name is empty", Toast.LENGTH_SHORT, true)
                .show()
            return
        }

        if (selectedCustomerType.isEmpty()) {
            Toasty.error(requireContext(), "Customer Type is empty", Toast.LENGTH_SHORT, true)
                .show()
            return
        }

        if (selectedCustomerType.equals("Walk In")) {
            if (selectedWaiter.isEmpty()) {
                Toasty.error(requireContext(), "Waiter is empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            if (selectedTable.isEmpty()) {
                Toasty.error(requireContext(), "Table is empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            orderInfo = OrderInfo(
                selectedCustomerInfo, selectedCustomerType, selectedWaiter, selectedTable, "", ""
            )
        } else if (selectedCustomerType.equals("Online Customer") || selectedCustomerType.equals("Take Way")) {
            if (selectedWaiter.isEmpty()) {
                Toasty.error(requireContext(), "Waiter is empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            orderInfo = OrderInfo(
                selectedCustomerInfo, selectedCustomerType, selectedWaiter, "", "", ""
            )
        } else {
            if (selectedDeliveryCompany.isEmpty()) {
                Toasty.error(
                    requireContext(), "Delivery Company is empty", Toast.LENGTH_SHORT, true
                ).show()
                return
            }

            if (oBinding.orderIdEt.text.toString().isEmpty()) {
                oBinding.orderIdEt.setError("Empty Value Can't Save")
                oBinding.orderIdEt.requestFocus()
                return
            }
            orderInfo = OrderInfo(
                selectedCustomerInfo,
                selectedCustomerType,
                "",
                "",
                selectedDeliveryCompany,
                oBinding.orderIdEt.text.toString().trim()
            )
            oBinding.orderIdEt.setText("")
        }

        sharedPref.writeOrderInfo(orderInfo)

        findNavController().popBackStack()
    }

    private fun tableAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val tBinding = DialogSingleItemetBinding.bind(
            layoutInflater.inflate(
                R.layout.dialog_single_itemet, null
            )
        )
        dialog.setContentView(tBinding.root)

        tBinding.itemTv.text = "Add Table"
        tBinding.itemEt.hint = "Enter Table name"

        tBinding.root.setOnClickListener {
            context?.let { it1 -> Util.hideSoftKeyBoard(it1, tBinding.root) }
        }

        tBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        tBinding.itemBtn.setOnClickListener {
            if (tBinding.itemEt.text.toString().isEmpty()) {
                tBinding.itemEt.setError("Table name empty")
                tBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertTable(Table(0, tBinding.itemEt.text.toString()))

                withContext(Dispatchers.Main) {
                    Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
                }
            }

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun waiterAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val waBinding = DialogSingleItemetBinding.bind(
            layoutInflater.inflate(
                R.layout.dialog_single_itemet, null
            )
        )
        dialog.setContentView(waBinding.root)

        waBinding.itemTv.text = "Add Waiter"
        waBinding.itemEt.hint = "Enter Waiter name"

        waBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), waBinding.root)
        }

        waBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        waBinding.itemBtn.setOnClickListener {
            if (waBinding.itemEt.text.toString().isEmpty()) {
                waBinding.itemEt.setError("Waiter name is empty")
                waBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertWaiter(Waiter(0, waBinding.itemEt.text.toString()))

                withContext(Dispatchers.Main) {
                    Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
                }
            }

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun deliveryCompanyAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val ctaBinding = DialogSingleItemetBinding.bind(
            layoutInflater.inflate(
                R.layout.dialog_single_itemet, null
            )
        )
        dialog.setContentView(ctaBinding.root)

        ctaBinding.itemTv.text = "Add Delivery Company"
        ctaBinding.itemEt.hint = "Enter Company name"

        ctaBinding.root.setOnClickListener {
            context?.let { it1 -> Util.hideSoftKeyBoard(it1, ctaBinding.root) }
        }

        ctaBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        ctaBinding.itemBtn.setOnClickListener {
            if (ctaBinding.itemEt.text.toString().isEmpty()) {
                ctaBinding.itemEt.setError("Company name is empty")
                ctaBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertDeliveryCompany(Company(0, ctaBinding.itemEt.text.toString()))

                withContext(Dispatchers.Main) {
                    Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
                }
            }

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}