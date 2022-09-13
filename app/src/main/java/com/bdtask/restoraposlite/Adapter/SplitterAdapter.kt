package com.bdtask.restoraposlite.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bdtask.restoraposlite.Dialog.SplitOrderDialog.Companion.splitterIndex
import com.bdtask.restoraposlite.Fragment.OngoingFragment.Companion.cusInfoList
import com.bdtask.restoraposlite.Fragment.OngoingFragment.Companion.cusNameList
import com.bdtask.restoraposlite.Interface.SplitterClickListener
import com.bdtask.restoraposlite.Model.CsInf
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Split
import com.bdtask.restoraposlite.databinding.VhSplitterBinding

class SplitterAdapter( private val context: Context,
                       private val splitterList: MutableList<Split>,
                       private val splitterClickListener: SplitterClickListener ): RecyclerView.Adapter<SplitterAdapter.VHSplitter>() {

    inner class VHSplitter(binding: VhSplitterBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHSplitter {
        return VHSplitter(VhSplitterBinding.bind(LayoutInflater.from(context).inflate(R.layout.vh_splitter,parent,false)))
    }

    override fun onBindViewHolder(holder: VHSplitter, position: Int) {
        val pos: Int = position

        holder.binding.orderId.text = "Order Id : " + splitterList[position].id

        if (splitterIndex == position){
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_selected)

            var tPrc = 0.0

            for (i in splitterList[splitterIndex].cart.indices){
                tPrc += splitterList[splitterIndex].cart[i].tUPrc
            }

            val vat = splitterList[splitterIndex].vat
            val crg = splitterList[splitterIndex].crg
            val gTotal = tPrc + vat + crg

            holder.binding.totalAmount.text = tPrc.toString()
            holder.binding.vat.text = vat.toString()
            holder.binding.serviceCharge.text = crg.toString()
            holder.binding.grandTotal.text = gTotal.toString()

        } else {
            holder.binding.splitter.setBackgroundResource(R.drawable.shape_splitter_unselect)
        }

        //holder.binding.grandTotal.text = splitterList[splitterIndex].cart[position].tUPrc + serviceCharge + vat

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

        holder.binding.splitPayNow.setOnClickListener {
            splitterClickListener.onPayNowClickListener()
        }
    }


    override fun getItemCount(): Int {
        return splitterList.size
    }
}