package com.bdtask.restoraposlite.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.adapters.SalesRepostAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.SharedPref
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.FragmentReportBinding
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import es.dmoral.toasty.Toasty
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private var orderList = mutableListOf<Order>()
    private var filteredList = mutableListOf<Order>()
    private val sharedPref = SharedPref

    companion object {
        var salesTotal = 0.0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        sharedPref.init(requireContext())


        binding.fromDateEt.setText(Util.getDate())
        binding.toDateEt.setText(Util.getDate())


        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getCOrder(1).observe(viewLifecycleOwner, Observer {
            orderList = it.asReversed().toMutableList()
            if (orderList.isNotEmpty()) {
                getAndSetRecyclerAdapter()
            }
        })


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.searchBtn.setOnClickListener {
            if (binding.searchEt.visibility == View.GONE) {
                binding.header.visibility = View.GONE
                binding.shareBtn.visibility = View.GONE
                binding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(binding.searchEt)
                binding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                binding.searchEt.visibility = View.GONE
                binding.header.visibility = View.VISIBLE
                binding.shareBtn.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), binding.root)
                binding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }


        binding.searchEt.doOnTextChanged { text, start, before, count ->

            val srsList = mutableListOf<Order>()

            if (text.toString() != "" && text.toString().isNotEmpty()) {
                srsList.clear()
                for (i in filteredList.indices) {
                    if (filteredList[i].id.toString()
                            .contains(text.toString()) || filteredList[i].token.contains(text.toString()) || filteredList[i].orderInfo.wtr.lowercase()
                            .contains(
                                text.toString().lowercase()
                            ) || filteredList[i].orderInfo.tbl.lowercase().contains(
                            text.toString().lowercase()
                        ) || filteredList[i].orderInfo.customerInfo.csNm.lowercase()
                            .contains(text.toString().lowercase())
                    ) {

                        srsList.add(filteredList[i])
                    }
                }

                if (srsList.isNotEmpty()) {
                    binding.emptyOrder.visibility = View.GONE
                    binding.rvLay.visibility = View.VISIBLE
                    binding.reportRecycler.adapter = SalesRepostAdapter(requireContext(), srsList)
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


        binding.totalSalesTv.text = ": $salesTotal $appCurrency"

        binding.shareBtn.setOnClickListener {

            if (binding.emptyOrder.visibility != View.VISIBLE) {

                PdfGenerator.getBuilder()
                    .setContext(requireContext())
                    .fromViewIDSource()
                    .fromViewID(requireActivity(), R.id.content)
                    .setFileName("${sharedPref.readResInf()?.name} Sales Report, from-${binding.fromDateEt.text.toString()}_to-${binding.toDateEt.text.toString()}")
                    .setFolderNameOrPath("${sharedPref.readResInf()?.name} Sales Report")
                    .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
                    .build(object : PdfGeneratorListener() {
                        override fun onFailure(failureResponse: FailureResponse) {
                            super.onFailure(failureResponse)
                            Log.wtf("FAIL", "")
                        }

                        override fun showLog(log: String) {
                            super.showLog(log)
                            Log.wtf("LOG", "")
                        }

                        override fun onStartPDFGeneration() {
                            Log.wtf("Start", "")
                        }

                        override fun onFinishPDFGeneration() {
                            Log.wtf("Finish", "")
                        }

                        override fun onSuccess(response: SuccessResponse) {
                            super.onSuccess(response)
                            Log.wtf("Success", "")
                        }
                    })
            } else {
                Toasty.error(requireContext(), "Nothing to Share", Toasty.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    private fun pickDate(task: Int) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePicker = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            { datePicker, year, month, day ->

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

            },
            year,
            month,
            day
        )

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

        if (dateIsValid(fromDate, toDate)) {
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

            for (i in orderList.indices) {
                try {
                    cDate = sdf.parse(orderList[i].date)
                } catch (e: Exception) {/**/
                }

                if (cDate != null) {

                    if (cDate >= frmDate && cDate <= tDate) {
                        filteredList.add(orderList[i])
                    }
                }

            }
            setRecyclerAdapter()
        }

    }

    private fun setTotalSales() {
        salesTotal = 0.0
        if (filteredList.size > 0) {
            for (i in filteredList.indices) {
                for (f in filteredList[i].payments.indices) {
                    salesTotal += filteredList[i].payments[f].amo
                }
            }
            binding.totalSalesTv.text = ": $salesTotal $appCurrency"
        } else {
            binding.totalSalesTv.text = ": $salesTotal $appCurrency"
        }
    }


    // checking from date & to date validation
    private fun dateIsValid(fromDate: String, toDate: String): Boolean {
        return if (fromDate.isNotEmpty() && toDate.isNotEmpty()) {
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
        if (filteredList.isNotEmpty()) {

            binding.emptyOrder.visibility = View.GONE
            binding.rvLay.visibility = View.VISIBLE
            binding.reportRecycler.adapter = SalesRepostAdapter(requireContext(), filteredList)

            setTotalSales()

        } else {
            binding.rvLay.visibility = View.GONE
            binding.emptyOrder.visibility = View.VISIBLE

            setTotalSales()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}