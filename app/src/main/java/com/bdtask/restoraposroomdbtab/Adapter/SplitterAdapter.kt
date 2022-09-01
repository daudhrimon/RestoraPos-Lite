package com.bdtask.restoraposroomdbtab.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposroomdbtab.Dialog.SplitOrder.Companion.splitterIndex
import com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion.cusInfoList
import com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion.cusNameList
import com.bdtask.restoraposroomdbtab.Model.CsInf
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Split
import com.bdtask.restoraposroomdbtab.databinding.VhSplitterBinding

class SplitterAdapter( private val context: Context,
                       private val splitterList: MutableList<Split> ): RecyclerView.Adapter<SplitterAdapter.VHSplitter>() {

    inner class VHSplitter(binding: VhSplitterBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitter {
        return VHSplitter(VhSplitterBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitter, position: Int) {
        val pos = position

        holder.binding.orderId.text = "Order Id : " + splitterList[position].id

        if (splitterIndex == position){
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_selected)
        } else {
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_unselect)
        }

        // itemView onClick
        holder.binding.splitter.setOnClickListener {
            if (splitterIndex == position){
                splitterIndex = -1
            } else {
                splitterIndex = position
            }
            notifyDataSetChanged()
        }


        // setting splitter details adapter
        holder.binding.splitterDetRv.adapter = SplitterDetailsAdapter(context, splitterList[position].cart)


        // setting Searchable Spinner
        holder.binding.cusInfoSpnr.adapter = ArrayAdapter(context, R.layout.custom_spinner_layout, cusNameList)


        // Customer Spinner
        holder.binding.cusInfoSpnr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spnrPos: Int, p3: Long) {

                val selectedItem =  holder.binding.cusInfoSpnr.selectedItem.toString()

                for (i in cusInfoList.indices){
                    if (cusInfoList[i].csNm == selectedItem && cusNameList[spnrPos] == cusInfoList[spnrPos].csNm){
                        splitterList[pos].csInf = CsInf(cusInfoList[i].csNm, cusInfoList[i].csAdrs, cusInfoList[i].mbl)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }
    }


    override fun getItemCount(): Int {
        return splitterList.size
    }
}