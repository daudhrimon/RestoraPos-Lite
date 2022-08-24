package com.bdtask.restoraposroomdbtab.Interface

import com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter

interface OngoingClickListener {
    fun onGoingItemClick(holder: OngoingAdapter.VHOngoing, position: Int, clickedList: ArrayList<Int>, multiSelect: Boolean)
}