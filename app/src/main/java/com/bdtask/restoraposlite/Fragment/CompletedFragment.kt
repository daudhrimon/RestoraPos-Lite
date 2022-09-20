package com.bdtask.restoraposlite.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.Adapter.ViewOrderAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.database
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Order
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.FragmentCompletedBinding

class CompletedFragment : Fragment() {

    private lateinit var binding: FragmentCompletedBinding
    private var orderList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedBinding.inflate(inflater, container, false)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


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



        database.AppDao().getCOrder(1).observe(viewLifecycleOwner, Observer {

            orderList = it.asReversed().toMutableList()

            setRecyclerAdapter()

        })




        binding.searchEt.doOnTextChanged { text, start, before, count ->

            val srsList = mutableListOf<Order>()

            if (text.toString() != "" && text.toString().isNotEmpty()){

                srsList.clear()

                for (i in orderList.indices){
                    if (orderList[i].id.toString().contains(text.toString()) ||
                        orderList[i].tkn.contains(text.toString()) ||
                        orderList[i].odrInf.wtr.lowercase().contains(text.toString().lowercase()) ||
                        orderList[i].odrInf.tbl.lowercase().contains(text.toString().lowercase()) ||
                        orderList[i].odrInf.csInf.csNm.lowercase().contains(text.toString().lowercase())){

                        srsList.add(orderList[i])
                    }
                }

                if (srsList.isNotEmpty()){
                    binding.emptyOrder.visibility = View.GONE
                    binding.rvLay.visibility = View.VISIBLE
                    binding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),srsList,lifecycleScope)
                } else {
                    binding.rvLay.visibility = View.GONE
                    binding.emptyOrder.visibility = View.VISIBLE
                }

            } else {

                setRecyclerAdapter()
            }
        }


        return binding.root
    }

    private fun setRecyclerAdapter() {
        if (orderList.isNotEmpty()){

            binding.emptyOrder.visibility = View.GONE
            binding.rvLay.visibility = View.VISIBLE
            binding.todayRecycler.adapter = ViewOrderAdapter(requireContext(),orderList,lifecycleScope)
        } else {
            binding.rvLay.visibility = View.GONE
            binding.emptyOrder.visibility = View.VISIBLE
        }
    }
}