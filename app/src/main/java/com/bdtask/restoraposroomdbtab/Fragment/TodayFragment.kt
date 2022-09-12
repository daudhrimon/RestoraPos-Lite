package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Adapter.ViewOrderAdapter
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.database
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Order
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.FragmentTodayBinding
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

                if (todayList.isNotEmpty()){

                    tBinding.emptyOrder.visibility = View.GONE
                    tBinding.rvLay.visibility = View.VISIBLE
                    tBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),todayList)
                } else {
                    tBinding.rvLay.visibility = View.GONE
                    tBinding.emptyOrder.visibility = View.VISIBLE
                }
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
                tBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),srsList)
            } else {
                tBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),todayList)
            }
        }

        return tBinding.root
    }
}