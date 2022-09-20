package com.bdtask.restoraposlite.Dialog

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import com.bdtask.restoraposlite.Adapter.CompanyEditAdapter
import com.bdtask.restoraposlite.Adapter.TableEditAdapter
import com.bdtask.restoraposlite.Adapter.WaiterEditAdapter
import com.bdtask.restoraposlite.Fragment.OrderInfoFragment.Companion.state
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cmpny
import com.bdtask.restoraposlite.Room.Entity.Table
import com.bdtask.restoraposlite.Room.Entity.Waiter
import com.bdtask.restoraposlite.databinding.BtmsheetItemEditDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BtmSItemRecyclerDialog(context: Context,
                             private val waiterList: MutableList<Waiter>,
                             private val dcList: MutableList<Cmpny>,
                             private val tbList: MutableList<Table>,
                             private val lifecycleScope: LifecycleCoroutineScope
                             ): BottomSheetDialog(context) {

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
                btmBinding.btmRecycler.adapter = WaiterEditAdapter(context,waiterList,lifecycleScope)
            }

            "dc" -> {
                btmBinding.btmRecycler.adapter = CompanyEditAdapter(context,dcList,lifecycleScope)
            }

            "tbl" -> {
                btmBinding.btmRecycler.adapter = TableEditAdapter(context,tbList,lifecycleScope)
            }
        }

    }
}