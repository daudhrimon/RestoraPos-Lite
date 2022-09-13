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
import com.bdtask.restoraposlite.databinding.FragmentCompletedBinding

class CompletedFragment : Fragment() {
    private lateinit var cBinding: FragmentCompletedBinding
    private var comList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        cBinding = FragmentCompletedBinding.inflate(inflater, container, false)


        cBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        cBinding.searchBtn.setOnClickListener {
            if (cBinding.searchEt.visibility == View.GONE){
                cBinding.header.visibility = View.GONE
                cBinding.searchEt.visibility = View.VISIBLE
                Util.showKeyboard(cBinding.searchEt)
                cBinding.searchBtn.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                cBinding.searchEt.visibility = View.GONE
                cBinding.header.visibility = View.VISIBLE
                Util.hideSoftKeyBoard(requireContext(), cBinding.root)
                cBinding.searchBtn.setImageResource(R.drawable.search_icon)
            }
        }



        database.orderDao().getCOrder(1).observe(viewLifecycleOwner, Observer {

            comList = it.toMutableList()

            setRecyclerAdapter()

        })




        cBinding.searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Order>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()
                for (i in comList.indices){
                    if (comList[i].id.toString().contains(text.toString()) ||
                        comList[i].tkn.contains(text.toString()) ||
                        comList[i].odrInf.wtr.lowercase().contains(text.toString().lowercase()) ||
                        comList[i].odrInf.tbl.lowercase().contains(text.toString().lowercase()) ||
                        comList[i].odrInf.csInf.csNm.lowercase().contains(text.toString().lowercase())){

                        srsList.add(comList[i])
                    }
                }
                if (srsList.isNotEmpty()){
                    cBinding.emptyOrder.visibility = View.GONE
                    cBinding.rvLay.visibility = View.VISIBLE
                    cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),srsList)
                } else {
                    cBinding.rvLay.visibility = View.GONE
                    cBinding.emptyOrder.visibility = View.VISIBLE
                }
            } else {

                setRecyclerAdapter()

            }
        }


        return cBinding.root
    }

    private fun setRecyclerAdapter() {
        if (comList.isNotEmpty()){

            cBinding.emptyOrder.visibility = View.GONE
            cBinding.rvLay.visibility = View.VISIBLE
            cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),comList)
        } else {
            cBinding.rvLay.visibility = View.GONE
            cBinding.emptyOrder.visibility = View.VISIBLE
        }
    }
}