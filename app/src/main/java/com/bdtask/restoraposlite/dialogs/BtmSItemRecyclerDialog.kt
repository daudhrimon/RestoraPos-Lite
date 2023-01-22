package com.bdtask.restoraposlite.dialogs

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import com.bdtask.restoraposlite.adapters.CompanyEditAdapter
import com.bdtask.restoraposlite.adapters.TableEditAdapter
import com.bdtask.restoraposlite.adapters.WaiterEditAdapter
import com.bdtask.restoraposlite.fragments.OrderInfoFragment.Companion.state
import com.bdtask.restoraposlite.room.entity.Company
import com.bdtask.restoraposlite.room.entity.Table
import com.bdtask.restoraposlite.room.entity.Waiter
import com.bdtask.restoraposlite.databinding.BtmsheetItemEditDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BtmSItemRecyclerDialog(
    context: Context,
    private val waiterList: MutableList<Waiter>,
    private val dcList: MutableList<Company>,
    private val tbList: MutableList<Table>,
    private val lifecycleScope: LifecycleCoroutineScope
) : BottomSheetDialog(context) {

    private lateinit var btmBinding: BtmsheetItemEditDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btmBinding = BtmsheetItemEditDeleteBinding.inflate(layoutInflater)
        setContentView(btmBinding.root)

        btmBinding.btmCrossBtn.setOnClickListener {
            dismiss()
        }

        when (state) {

            "wtr" -> {
                btmBinding.btmRecycler.adapter =
                    WaiterEditAdapter(context, waiterList, lifecycleScope)
            }

            "dc" -> {
                btmBinding.btmRecycler.adapter = CompanyEditAdapter(context, dcList, lifecycleScope)
            }

            "tbl" -> {
                btmBinding.btmRecycler.adapter = TableEditAdapter(context, tbList, lifecycleScope)
            }
        }

    }
}