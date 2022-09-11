package com.bdtask.restoraposroomdbtab.Dialog

import android.content.Context
import android.os.Bundle
import com.bdtask.restoraposroomdbtab.Adapter.CompanyEditAdapter
import com.bdtask.restoraposroomdbtab.Adapter.TableEditAdapter
import com.bdtask.restoraposroomdbtab.Adapter.WaiterEditAdapter
import com.bdtask.restoraposroomdbtab.Fragment.OrderInfoFragment.Companion.state
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Cmpny
import com.bdtask.restoraposroomdbtab.Room.Entity.Table
import com.bdtask.restoraposroomdbtab.Room.Entity.Waiter
import com.bdtask.restoraposroomdbtab.databinding.BtmsheetItemEditDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BtmSItemRecyclerDialog(context: Context,
                             private val waiterList: MutableList<Waiter>,
                             private val dcList: MutableList<Cmpny>,
                             private val tbList: MutableList<Table> ): BottomSheetDialog(context) {

    private lateinit var btmBinding: BtmsheetItemEditDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btmBinding = BtmsheetItemEditDeleteBinding.bind(layoutInflater.inflate(R.layout.btmsheet_item_edit_delete,null))
        setContentView(btmBinding.root)

        btmBinding.btmCrossBtn.setOnClickListener {
            dismiss()
        }

        when(state){
            "wtr" -> {
                btmBinding.btmRecycler.adapter = WaiterEditAdapter(context,waiterList)
            }

            "dc" -> {
                btmBinding.btmRecycler.adapter = CompanyEditAdapter(context,dcList)
            }

            "tbl" -> {
                btmBinding.btmRecycler.adapter = TableEditAdapter(context,tbList)
            }
        }

    }
}