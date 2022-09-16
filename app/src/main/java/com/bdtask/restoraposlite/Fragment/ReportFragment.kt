package com.bdtask.restoraposlite.Fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.Adapter.SalesRepostAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.FragmentReportBinding
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private var orderList = mutableListOf<Order>()
    private var filteredList = mutableListOf<Order>()
    companion object { var salesTotal = 0.0 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)



        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        database.AppDao().getCOrder(1).observe(viewLifecycleOwner, Observer {

            orderList = it.toMutableList()

        })



        binding.fromDateEt.setText(Util.getDate())



        binding.searchBtn.setOnClickListener {
            if (binding.searchEt.visibility == View.GONE){
                binding.header.visibility = View.GONE
                binding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(binding.searchEt)
                binding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                binding.searchEt.visibility = View.GONE
                binding.header.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), binding.root)
                binding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }




        binding.searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Order>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()
                for (i in filteredList.indices){
                    if (filteredList[i].id.toString().contains(text.toString()) ||
                        filteredList[i].tkn.contains(text.toString()) ||
                        filteredList[i].odrInf.wtr.lowercase().contains(text.toString().lowercase()) ||
                        filteredList[i].odrInf.tbl.lowercase().contains(text.toString().lowercase()) ||
                        filteredList[i].odrInf.csInf.csNm.lowercase().contains(text.toString().lowercase())){

                        srsList.add(filteredList[i])
                    }
                }
                if (srsList.isNotEmpty()){
                    binding.emptyOrder.visibility = View.GONE
                    binding.rvLay.visibility = View.VISIBLE
                    binding.todayRecycler.adapter = SalesRepostAdapter(requireContext(),srsList)
                } else {
                    binding.rvLay.visibility = View.GONE
                    binding.emptyOrder.visibility = View.VISIBLE
                }
            } else {

                setRecyclerAdapter()

            }
        }


        binding.fromDateEt.setOnClickListener {
            pickDate(0)
        }


        binding.toDateEt.setOnClickListener {
            pickDate(1)
        }


        binding.totalSalesTv.text = " : $salesTotal $appCurrency"


        return binding.root
    }

    private fun pickDate(task: Int) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePicker = DatePickerDialog(requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth, { datePicker, year, month, day ->

                var mon = month
                mon += 1

                val date = if (month in 1..9) {
                    "$day-0$mon-$year"
                } else {
                    "$day-$mon-$year"
                }

                if (task == 0) {
                    binding.fromDateEt.setText(date)
                } else {
                    binding.toDateEt.setText(date)
                }

                getAndSetRecyclerAdapter()

            }, year, month, day)

        datePicker.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (task == 0) {
            datePicker.setTitle("From Date")
        } else {
            datePicker.setTitle("To Date")
        }
        datePicker.setCancelable(false)
        datePicker.show()
    }




    private fun getAndSetRecyclerAdapter() {
        val fromDate = binding.fromDateEt.text.toString()
        val toDate = binding.toDateEt.text.toString()

        if (dateIsValid(fromDate,toDate)){
            salesTotal = 0.0
            filteredList.clear()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            var frmDate: Date? = null
            var tDate: Date? = null
            var cDate: Date? = null

            try {
                frmDate = sdf.parse(fromDate)
                tDate = sdf.parse(toDate)
            } catch (e: ParseException) {/**/}

            for ( i in orderList.indices){
                try {
                    cDate = sdf.parse(orderList[i].dat)
                } catch (e: Exception){/**/}

                if (cDate != null){

                    if (cDate >= frmDate && cDate <= tDate){
                        filteredList.add(orderList[i])
                    }
                }

            }

            setRecyclerAdapter()
        }

    }

    private fun setTotalSales() {
        salesTotal = 0.0
        if (filteredList.size > 0){
            for (i in filteredList.indices){
                for (f in filteredList[i].pay.indices){
                    salesTotal += filteredList[i].pay[f].amo
                }
            }
            binding.totalSalesTv.text = " : $salesTotal $appCurrency"
        } else {
            binding.totalSalesTv.text = " : $salesTotal $appCurrency"
        }
    }


    // checking from date & to date validation
    private fun dateIsValid(fromDate: String, toDate: String): Boolean {
        return if (fromDate.isNotEmpty() && toDate.isNotEmpty()){
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            var frmDate: Date? = null
            var tDate: Date? = null
            try {
                frmDate = sdf.parse(fromDate)
                tDate = sdf.parse(toDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            frmDate!!.time <= tDate!!.time
        } else {
            false
        }
    }

    private fun setRecyclerAdapter() {
        if (filteredList.isNotEmpty()){

            binding.emptyOrder.visibility = View.GONE
            binding.rvLay.visibility = View.VISIBLE
            binding.todayRecycler.adapter = SalesRepostAdapter(requireContext(),filteredList)

            setTotalSales()

        } else {
            binding.rvLay.visibility = View.GONE
            binding.emptyOrder.visibility = View.VISIBLE

            setTotalSales()
        }
    }
}