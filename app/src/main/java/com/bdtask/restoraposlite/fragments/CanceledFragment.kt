package com.bdtask.restoraposlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.adapters.ViewOrderAdapter
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Order
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.FragmentCanceledBinding

class CanceledFragment : Fragment() {
    private var _binding: FragmentCanceledBinding? = null
    private val binding get() = _binding!!
    private var orderList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanceledBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.searchBtn.setOnClickListener {
            if (binding.searchEt.visibility == View.GONE) {
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


        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getCOrder(2).observe(viewLifecycleOwner, Observer {

            orderList = it.asReversed().toMutableList()

            setRecyclerAdapter()

        })




        binding.searchEt.doOnTextChanged { text, start, before, count ->

            val srsList = mutableListOf<Order>()

            if (text.toString() != "" && text.toString().isNotEmpty()) {

                srsList.clear()

                for (i in orderList.indices) {
                    if (orderList[i].id.toString()
                            .contains(text.toString()) || orderList[i].token.contains(text.toString()) || orderList[i].orderInfo.wtr.lowercase()
                            .contains(
                                text.toString().lowercase()
                            ) || orderList[i].orderInfo.tbl.lowercase().contains(
                            text.toString().lowercase()
                        ) || orderList[i].orderInfo.customerInfo.csNm.lowercase()
                            .contains(text.toString().lowercase())
                    ) {

                        srsList.add(orderList[i])
                    }
                }

                if (srsList.isNotEmpty()) {
                    binding.emptyOrder.visibility = View.GONE
                    binding.rvLay.visibility = View.VISIBLE
                    binding.todayRecycler.adapter =
                        ViewOrderAdapter(requireContext(), srsList, lifecycleScope)
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
        if (orderList.isNotEmpty()) {

            binding.emptyOrder.visibility = View.GONE
            binding.rvLay.visibility = View.VISIBLE
            binding.todayRecycler.adapter =
                ViewOrderAdapter(requireContext(), orderList, lifecycleScope)
        } else {
            binding.rvLay.visibility = View.GONE
            binding.emptyOrder.visibility = View.VISIBLE
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}