package com.bdtask.restoraposroomdbtab.Fragment

import android.os.Bundle
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
import com.bdtask.restoraposroomdbtab.databinding.FragmentCanceledBinding

class CanceledFragment : Fragment() {
    private lateinit var cBinding: FragmentCanceledBinding
    private var canList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        cBinding = FragmentCanceledBinding.inflate(inflater, container, false)


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


        database.orderDao().getCOrder(2).observe(viewLifecycleOwner, Observer {

            canList = it.toMutableList()

            if (canList.isNotEmpty()){

                cBinding.emptyOrder.visibility = View.GONE
                cBinding.rvLay.visibility = View.VISIBLE
                cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),canList)
            } else {
                cBinding.rvLay.visibility = View.GONE
                cBinding.emptyOrder.visibility = View.VISIBLE
            }
        })




        cBinding.searchEt.doOnTextChanged { text, start, before, count ->
            val srsList = mutableListOf<Order>()
            if (text.toString() != "" && text.toString().isNotEmpty()){
                srsList.clear()
                for (i in canList.indices){
                    if (canList[i].id.toString().contains(text.toString()) ||
                        canList[i].tkn.contains(text.toString()) ||
                            canList[i].odrInf.wtr.lowercase().contains(text.toString().lowercase()) ||
                        canList[i].odrInf.tbl.lowercase().contains(text.toString().lowercase()) ||
                        canList[i].odrInf.csInf.csNm.lowercase().contains(text.toString().lowercase())){

                        srsList.add(canList[i])
                    }
                }
                cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),srsList)
            } else {
                cBinding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),canList)
            }
        }

        return cBinding.root
    }
}