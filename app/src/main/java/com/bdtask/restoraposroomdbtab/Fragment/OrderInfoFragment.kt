package com.bdtask.restoraposroomdbtab.Fragment

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
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.Model.CustomerInfo
import com.bdtask.restoraposroomdbtab.Model.OrderInfo
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Customer
import com.bdtask.restoraposroomdbtab.Room.Entity.Company
import com.bdtask.restoraposroomdbtab.Room.Entity.Table
import com.bdtask.restoraposroomdbtab.Room.Entity.Waiter
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogAddNewCustomerBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogSingleItemetBinding
import com.bdtask.restoraposroomdbtab.databinding.FragmentOrderInfoBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderInfoFragment : Fragment() {
    private lateinit var binding: FragmentOrderInfoBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderInfoBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())

        // getting  customer name live and setting to spinner live
        MainActivity.database.customerDao().getAllCustomer().observe(viewLifecycleOwner, Observer{
            cusNameList.clear()
            for (i in it.indices){
                cusNameList.add(it[i].name)
                cusInfoList.add(CustomerInfo(it[i].name, it[i].address, it[i].mobile))
            }
            binding.cusNameSpnr.adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout,cusNameList)
        })

        // Customer Spinner
        binding.cusNameSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                val selectedItem =  binding.cusNameSpnr.selectedItem.toString()

                for (i in cusInfoList.indices){
                    if (cusInfoList[i].cusName == selectedItem && cusNameList[spnrPos] == cusInfoList[spnrPos].cusName){
                        selectedCustomerInfo = CustomerInfo(cusInfoList[i].cusName, cusInfoList[i].cusAddress, cusInfoList[i].mobile)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        // Customer Type Spinner Set
        cusTypeList = arrayListOf<String>("Walk In", "Take Way", "Online Customer", "Third Party")
        binding.cusTypeSpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, cusTypeList)

        // customer Type Spinner select Listener
        binding.cusTypeSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCustomerType = binding.cusTypeSpnr.selectedItem.toString()
                if (selectedCustomerType.equals("Walk In")) {
                    binding.waiterLay.visibility = View.VISIBLE
                    binding.tableLay.visibility = View.VISIBLE
                    binding.deliveryCompanyLay.visibility = View.GONE
                    binding.orderIdLay.visibility = View.GONE
                } else if (selectedCustomerType.equals("Online Customer")
                    || selectedCustomerType.equals("Take Way")) {
                    binding.waiterLay.visibility = View.VISIBLE
                    binding.tableLay.visibility = View.GONE
                    binding.deliveryCompanyLay.visibility = View.GONE
                    binding.orderIdLay.visibility = View.GONE
                } else {
                    binding.waiterLay.visibility = View.GONE
                    binding.tableLay.visibility = View.GONE
                    binding.deliveryCompanyLay.visibility = View.VISIBLE
                    binding.orderIdLay.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        //getting waiter Live and setting to Waiter spinner Live
        MainActivity.database.waiterDao().getAllWaiter().observe(viewLifecycleOwner, Observer{
            waiterList.clear()
            waiterSpnrList.clear()
            waiterList = it.toMutableList()
            for (i in waiterList.indices){
                waiterSpnrList.add(waiterList[i].wName)
            }
            binding.waiterSpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, waiterSpnrList)
        })

        //Waiter Spinner
        binding.waiterSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedWaiter = binding.waiterSpnr.selectedItem.toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        // getting and setting Table Spinner
        MainActivity.database.tableDao().getAllTable().observe(viewLifecycleOwner, Observer{
            tableList.clear()
            tableList = it.toMutableList()
            tableSpnrList.clear()
            for (i in tableList.indices){
                tableSpnrList.add(tableList[i].tName)
            }
            binding.tableSpnr.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tableSpnrList)
        })

        // table spinner selected listener
        binding.tableSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {
                selectedTable = binding.tableSpnr.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        //getting delivery Company Live and setting to spinner live
        MainActivity.database.deliveryCompanyDao().getDeliveryCompany().observe(viewLifecycleOwner, Observer{
            deliveryCompanyList.clear()
            deliveryCompanyList.clear()
            deliveryCompanyList = it.toMutableList()
            for (i in deliveryCompanyList.indices){
                deliveryCompanySpnrList.add(deliveryCompanyList[i].companyName)
            }
            binding.deliveryCompanySpnr.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, deliveryCompanySpnrList)
        })

        // delivery Company Spinner
        binding.deliveryCompanySpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDeliveryCompany = binding.deliveryCompanySpnr.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        // back button click handler
        binding.ocfBack.setOnClickListener{ findNavController().popBackStack() }

        binding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), binding.root) }

        binding.cusAddBtn.setOnClickListener {
            addNewCustomer()
        }

        binding.deliveryCmpnyAddBtn.setOnClickListener {
            deliveryCompanyAddBtnClick()
        }

        binding.waiterAddBtn.setOnClickListener {
            waiterAddBtnClick()
        }

        binding.tableAddBtn.setOnClickListener {
            tableAddBtnClick()
        }

        binding.doneBtn.setOnClickListener {
            //findNavController().popBackStack()
            doneButtonClickHandler()
        }

        return binding.root
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

            orderInfo = OrderInfo(
                    selectedCustomerInfo,
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

            orderInfo = OrderInfo(
                    selectedCustomerInfo,
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

            if (binding.orderIdEt.text.toString().isEmpty()) {
                binding.orderIdEt.setError("Empty Value Can't Save")
                binding.orderIdEt.requestFocus()
                return
            }
            orderInfo = OrderInfo(
                    selectedCustomerInfo,
                    selectedCustomerType,
                    "",
                    "",
                    selectedDeliveryCompany,
                    binding.orderIdEt.text.toString().trim()
                )
            binding.orderIdEt.setText("")
        }

        sharedPref.writeSharedOrderInfoList(orderInfo)

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
                MainActivity.database.deliveryCompanyDao().insertDeliveryCompany(Company(0,ctaBinding.itemEt.text.toString()))
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

    private fun addNewCustomer() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val ancBinding = DialogAddNewCustomerBinding.bind(layoutInflater.inflate(R.layout.dialog_add_new_customer,null))
        dialog.setContentView(ancBinding.root)

        ancBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),ancBinding.root)
        }
        ancBinding.addCview.setOnClickListener{
            Util.hideSoftKeyBoard(requireContext(),ancBinding.addCview)
        }
        ancBinding.crossBtn.setOnClickListener {
            dialog.dismiss()
        }
        ancBinding.closeBtn.setOnClickListener {
            dialog.dismiss()
        }

        ancBinding.submitBtn.setOnClickListener {
            if (ancBinding.cusNameEt.text.toString().isEmpty()){
                ancBinding.cusNameEt.setError("Name is Empty")
                ancBinding.cusNameEt.requestFocus()
                return@setOnClickListener
            }
            if (ancBinding.cusEmailEt.text.toString().isEmpty()){
                ancBinding.cusEmailEt.setError("Email is Empty")
                ancBinding.cusEmailEt.requestFocus()
                return@setOnClickListener
            }
            if (ancBinding.cusMobileEt.text.toString().isEmpty()){
                ancBinding.cusMobileEt.setError("Mobile is Empty")
                ancBinding.cusMobileEt.requestFocus()
                return@setOnClickListener
            }
            if (ancBinding.cusAddEt.text.toString().isEmpty()){
                ancBinding.cusAddEt.setError("Address is Empty")
                ancBinding.cusAddEt.requestFocus()
                return@setOnClickListener
            }
            if (ancBinding.cusNameEt.text.toString().isEmpty()){
                ancBinding.cusNameEt.setError("Favourite Address is Empty")
                ancBinding.cusNameEt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {
                MainActivity.database.customerDao().insertCustomer(Customer(0,ancBinding.cusNameEt.text.toString(),ancBinding.cusEmailEt.text.toString(),
                    ancBinding.cusMobileEt.text.toString(),ancBinding.cusAddEt.text.toString(),ancBinding.cusNameEt.text.toString()))
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