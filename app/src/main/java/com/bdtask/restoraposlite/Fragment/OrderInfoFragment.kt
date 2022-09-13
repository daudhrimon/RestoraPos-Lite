package com.bdtask.restoraposlite.Fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.Dialog.AddCustomerDialog
import com.bdtask.restoraposlite.Dialog.BtmSItemRecyclerDialog
import com.bdtask.restoraposlite.MainActivity
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.Model.CsInf
import com.bdtask.restoraposlite.Model.OdrInf
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cmpny
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Room.Entity.Table
import com.bdtask.restoraposlite.Room.Entity.Waiter
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposlite.databinding.FragmentOrderInfoBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderInfoFragment : Fragment() {
    private lateinit var oBinding: FragmentOrderInfoBinding
    private var cusNameList = mutableListOf<String>()
    private var cusInfoList = mutableListOf<CsInf>()
    private var deliveryCmpnyList = mutableListOf<Cmpny>()
    private var deliveryCompanySpnrList = mutableListOf<String>()
    private var cusTypeList = arrayListOf<String>()
    private var waiterList = mutableListOf<Waiter>()
    private var waiterSpnrList = mutableListOf<String>()
    private var tableList = mutableListOf<Table>()
    private var tableSpnrList = mutableListOf<String>()
    private lateinit var selectedCsInf: CsInf
    private var selectedCustomerType = ""
    private var selectedWaiter = ""
    private var selectedTable = ""
    private var selectedDeliveryCompany = ""
    private lateinit var odrInf: OdrInf
    private var sharedPref = SharedPref
    private var odrInf2: OdrInf? = null
    private var btmDialog: BtmSItemRecyclerDialog? = null
    companion object {var state = ""}
    private var customerList = mutableListOf<Cstmr>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        oBinding = FragmentOrderInfoBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())


        // get Shared/Saved Customer Info
        odrInf2 = sharedPref.readOrderInfo()



        oBinding.ocfBack.setOnClickListener {
            findNavController().popBackStack()
        }



        // getting  customer name live and setting to spinner live
        database.customerDao().getAllCustomer().observe(viewLifecycleOwner, Observer{
            customerList.clear()
            cusNameList.clear()
            cusInfoList.clear()
            customerList = it.toMutableList()
            for (i in it.indices){
                cusNameList.add(it[i].nm)
                cusInfoList.add(CsInf(it[i].nm, it[i].adrs, it[i].mbl))
            }
            oBinding.cusNameSpnr.adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout,cusNameList)

            if (odrInf2 != null){
                if (odrInf2!!.csInf.csNm.isNotEmpty()){
                    for (i in cusInfoList.indices){
                        if (cusInfoList[i].csNm == odrInf2!!.csInf.csNm &&
                            cusInfoList[i].csAdrs == odrInf2!!.csInf.csAdrs &&
                            cusInfoList[i].mbl == odrInf2!!.csInf.mbl){

                            oBinding.cusNameSpnr.setSelection(i)
                        }
                    }
                }
            }
        })



        // Customer Spinner
        oBinding.cusNameSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {

                val selectedItem =  oBinding.cusNameSpnr.selectedItem.toString()

                for (i in cusInfoList.indices){
                    if (cusInfoList[i].csNm == selectedItem && cusNameList[spnrPos] == cusInfoList[spnrPos].csNm){
                        selectedCsInf = CsInf(cusInfoList[i].csNm, cusInfoList[i].csAdrs, cusInfoList[i].mbl)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }




        // Customer Type Spinner Set
        cusTypeList = arrayListOf<String>("Walk In", "Take Way", "Online Customer", "Third Party")
        oBinding.cusTypeSpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, cusTypeList)




        // customer Type Spinner select Listener
        oBinding.cusTypeSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCustomerType = oBinding.cusTypeSpnr.selectedItem.toString()
                if (selectedCustomerType.equals("Walk In")) {
                    oBinding.waiterLay.visibility = View.VISIBLE
                    oBinding.tableLay.visibility = View.VISIBLE
                    oBinding.deliveryCompanyLay.visibility = View.GONE
                    oBinding.orderIdLay.visibility = View.GONE
                } else if (selectedCustomerType.equals("Online Customer")
                    || selectedCustomerType.equals("Take Way")) {
                    oBinding.waiterLay.visibility = View.VISIBLE
                    oBinding.tableLay.visibility = View.GONE
                    oBinding.deliveryCompanyLay.visibility = View.GONE
                    oBinding.orderIdLay.visibility = View.GONE
                } else {
                    oBinding.waiterLay.visibility = View.GONE
                    oBinding.tableLay.visibility = View.GONE
                    oBinding.deliveryCompanyLay.visibility = View.VISIBLE
                    oBinding.orderIdLay.visibility = View.VISIBLE
                    if (odrInf2 != null){
                        if (odrInf2!!.odrIdTp.isNotEmpty()){
                            oBinding.orderIdEt.setText(odrInf2!!.odrIdTp)
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }





        //getting waiter Live and setting to Waiter spinner Live
        database.waiterDao().getAllWaiter().observe(viewLifecycleOwner, Observer{
            waiterList.clear()
            waiterSpnrList.clear()
            waiterList = it.toMutableList()
            for (i in waiterList.indices){
                waiterSpnrList.add(waiterList[i].wNm)
            }
            oBinding.waiterSpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, waiterSpnrList)

            if (btmDialog != null){
                if (btmDialog!!.isShowing && state == "wtr"){
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(requireContext(),waiterList, emptyList<Cmpny>().toMutableList(),
                        emptyList<Table>().toMutableList())
                    btmDialog?.show()
                }
            }

            if (odrInf2 != null){
                if (odrInf2!!.wtr.isNotEmpty()){
                    for (i in waiterSpnrList.indices){
                        if (waiterSpnrList[i] == odrInf2!!.wtr){
                            oBinding.waiterSpnr.setSelection(i)
                        }
                    }
                }
            }
        })





        //Waiter Spinner
        oBinding.waiterSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedWaiter = oBinding.waiterSpnr.selectedItem.toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }




        // getting and setting Table Spinner
        database.tableDao().getAllTable().observe(viewLifecycleOwner, Observer{
            tableList.clear()
            tableList = it.toMutableList()
            tableSpnrList.clear()
            for (i in tableList.indices){
                tableSpnrList.add(tableList[i].tNm)
            }

            oBinding.tableSpnr.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tableSpnrList)

            if (btmDialog != null){
                if (btmDialog!!.isShowing && state == "tbl"){
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(requireContext(),emptyList<Waiter>().toMutableList(),emptyList<Cmpny>().toMutableList(),tableList)
                    btmDialog?.show()
                }
            }

            if (odrInf2 != null){
                if (odrInf2!!.tbl.isNotEmpty()){
                    for (i in tableSpnrList.indices){
                        if (tableSpnrList[i] == odrInf2!!.tbl){
                            oBinding.tableSpnr.setSelection(i)
                        }
                    }
                }
            }
        })




        // table spinner selected listener
        oBinding.tableSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedTable = oBinding.tableSpnr.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }




        //getting delivery Company Live and setting to spinner live
        database.deliveryCompanyDao().getDeliveryCompany().observe(viewLifecycleOwner, Observer{
            deliveryCmpnyList.clear()
            deliveryCmpnyList.clear()
            deliveryCmpnyList = it.toMutableList()
            for (i in deliveryCmpnyList.indices){
                deliveryCompanySpnrList.add(deliveryCmpnyList[i].cNm)
            }
            oBinding.deliveryCompanySpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, deliveryCompanySpnrList)

            if (btmDialog != null){
                if (btmDialog!!.isShowing && state == "dc"){
                    btmDialog?.dismiss()
                    btmDialog = BtmSItemRecyclerDialog(requireContext(),waiterList, emptyList<Cmpny>().toMutableList(),emptyList<Table>().toMutableList())
                    btmDialog?.show()
                }
            }

            if (odrInf2 != null){
                if (odrInf2!!.dlvCo.isNotEmpty()){
                    for (i in deliveryCompanySpnrList.indices){
                        if (deliveryCompanySpnrList[i] == odrInf2!!.dlvCo){
                            oBinding.deliveryCompanySpnr.setSelection(i)
                        }
                    }
                }
            }
        })




        // delivery Company Spinner
        oBinding.deliveryCompanySpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDeliveryCompany = oBinding.deliveryCompanySpnr.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }





        oBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), oBinding.root) }

        oBinding.cusAddBtn.setOnClickListener {
            val dialog = AddCustomerDialog(requireContext(),0)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            val width = resources.displayMetrics.widthPixels
            val win = dialog.window
            win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        oBinding.deliveryCmpnyAddBtn.setOnClickListener {
            deliveryCompanyAddBtnClick()
        }

        oBinding.deliveryCmpnyAddBtn.setOnLongClickListener {
            state = "dc"
            btmDialog = BtmSItemRecyclerDialog(requireContext(),emptyList<Waiter>().toMutableList(),deliveryCmpnyList,emptyList<Table>().toMutableList())
            btmDialog?.show()
            return@setOnLongClickListener true
        }

        oBinding.waiterAddBtn.setOnClickListener {
            waiterAddBtnClick()
        }

        oBinding.waiterAddBtn.setOnLongClickListener {
            state = "wtr"
            btmDialog = BtmSItemRecyclerDialog(requireContext(),waiterList,emptyList<Cmpny>().toMutableList(),emptyList<Table>().toMutableList())
            btmDialog?.show()
            return@setOnLongClickListener true
        }

        oBinding.tableAddBtn.setOnClickListener {
            tableAddBtnClick()
        }

        oBinding.tableAddBtn.setOnLongClickListener {
            state = "tbl"
            btmDialog = BtmSItemRecyclerDialog(requireContext(),emptyList<Waiter>().toMutableList(),emptyList<Cmpny>().toMutableList(),tableList)
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
            Toasty.error(requireContext(),"Customer Name is Empty", Toast.LENGTH_SHORT, true).show()
            return
        }

        if (selectedCustomerType.isEmpty()) {
            Toasty.error(requireContext(),"Customer Type is Empty", Toast.LENGTH_SHORT, true).show()
            return
        }

        if (selectedCustomerType.equals("Walk In")) {
            if (selectedWaiter.isEmpty()) {
                Toasty.error(requireContext(),"Waiter is Empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            if (selectedTable.isEmpty()) {
                Toasty.error(requireContext(),"Table is Empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            odrInf = OdrInf(
                    selectedCsInf,
                    selectedCustomerType,
                    selectedWaiter,
                    selectedTable,
                    "",
                    ""
                )
        } else if (selectedCustomerType.equals("Online Customer") || selectedCustomerType.equals("Take Way")) {
            if (selectedWaiter.isEmpty()) {
                Toasty.error(requireContext(),"Waiter is Empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            odrInf = OdrInf(
                    selectedCsInf,
                    selectedCustomerType,
                    selectedWaiter,
                    "",
                    "",
                    ""
                )
        } else {
            if (selectedDeliveryCompany.isEmpty()) {
                Toasty.error(requireContext(),"Delivery Company is Empty", Toast.LENGTH_SHORT, true).show()
                return
            }

            if (oBinding.orderIdEt.text.toString().isEmpty()) {
                oBinding.orderIdEt.setError("Empty Value Can't Save")
                oBinding.orderIdEt.requestFocus()
                return
            }
            odrInf = OdrInf(
                    selectedCsInf,
                    selectedCustomerType,
                    "",
                    "",
                    selectedDeliveryCompany,
                    oBinding.orderIdEt.text.toString().trim()
                )
            oBinding.orderIdEt.setText("")
        }

        sharedPref.writeOrderInfo(odrInf)

        findNavController().popBackStack()
    }

    private fun tableAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val tBinding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        dialog.setContentView(tBinding.root)

        tBinding.itemTv.text = "Add Table"
        tBinding.itemEt.hint = "Enter Table Name"

        tBinding.root.setOnClickListener {
            context?.let { it1 -> Util.hideSoftKeyBoard(it1,tBinding.root) }
        }

        tBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        tBinding.itemBtn.setOnClickListener {
            if (tBinding.itemEt.text.toString().isEmpty()){
                tBinding.itemEt.setError("Table Name Empty")
                tBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {
                MainActivity.database.tableDao().insertTable(Table(0,tBinding.itemEt.text.toString()))
            }

            Toasty.success(requireContext(),"Successful", Toast.LENGTH_SHORT, true).show()
            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun waiterAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val waBinding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        dialog.setContentView(waBinding.root)

        waBinding.itemTv.text = "Add Waiter"
        waBinding.itemEt.hint = "Enter Waiter Name"

        waBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),waBinding.root)
        }

        waBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        waBinding.itemBtn.setOnClickListener {
            if (waBinding.itemEt.text.toString().isEmpty()){
                waBinding.itemEt.setError("Waiter Name Empty")
                waBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {
                MainActivity.database.waiterDao().insertWaiter(Waiter(0,waBinding.itemEt.text.toString()))
            }

            Toasty.success(requireContext(),"Successful", Toast.LENGTH_SHORT, true).show()
            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun deliveryCompanyAddBtnClick() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val ctaBinding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        dialog.setContentView(ctaBinding.root)

        ctaBinding.itemTv.text = "Add Delivery Company"
        ctaBinding.itemEt.hint = "Enter Company Name"

        ctaBinding.root.setOnClickListener {
            context?.let { it1 -> Util.hideSoftKeyBoard(it1,ctaBinding.root) }
        }

        ctaBinding.itemCross.setOnClickListener {
            dialog.dismiss()
        }

        ctaBinding.itemBtn.setOnClickListener {
            if (ctaBinding.itemEt.text.toString().isEmpty()){
                ctaBinding.itemEt.setError("Company Name Empty")
                ctaBinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {
                MainActivity.database.deliveryCompanyDao().insertDeliveryCompany(Cmpny(0,ctaBinding.itemEt.text.toString()))
            }

            Toasty.success(requireContext(),"Successful", Toast.LENGTH_SHORT, true).show()
            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}