package com.bdtask.restoraposlite.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.Adapter.ViewOrderAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.FragmentTodayBinding
import java.text.SimpleDateFormat
import java.util.*

class TodayFragment() : Fragment() {
    private lateinit var tBinding: FragmentTodayBinding
    private var todayList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        tBinding = FragmentTodayBinding.inflate(inflater, container, false)


        tBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        tBinding.searchBtn.setOnClickListener {
            if (tBinding.searchEt.visibility == View.GONE){
                tBinding.header.visibility = View.GONE
                tBinding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(tBinding.searchEt)
                tBinding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                tBinding.searchEt.visibility = View.GONE
                tBinding.header.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), tBinding.root)
                tBinding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }


        database.orderDao()
            .getTodayOrder(SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),1)
            .observe(viewLifecycleOwner, Observer {

                todayList = it

                setRecyclerAdapter()

            })



        tBinding.searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Order>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()
                for (i in todayList.indices){
                    if (todayList[i].id.toString().contains(text.toString()) ||
                        todayList[i].tkn.contains(text.toString()) ||
                        todayList[i].odrInf.wtr.lowercase().contains(text.toString().lowercase()) ||
                        todayList[i].odrInf.tbl.lowercase().contains(text.toString().lowercase()) ||
                        todayList[i].odrInf.csInf.csNm.lowercase().contains(text.toString().lowercase())){

                        srsList.add(todayList[i])
                    }
                }
                if (srsList.isNotEmpty()){
                    tBinding.emptyOrder.visibility = View.GONE
                    tBinding.rvLay.visibility = View.VISIBLE
                    tBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),srsList)
                } else {
                    tBinding.rvLay.visibility = View.GONE
                    tBinding.emptyOrder.visibility = View.VISIBLE
                }
            } else {

                setRecyclerAdapter()

            }
        }

        return tBinding.root
    }

    private fun setRecyclerAdapter() {
        if (todayList.isNotEmpty()){

            tBinding.emptyOrder.visibility = View.GONE
            tBinding.rvLay.visibility = View.VISIBLE
            tBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),todayList)
        } else {
            tBinding.rvLay.visibility = View.GONE
            tBinding.emptyOrder.visibility = View.VISIBLE
        }
    }
}