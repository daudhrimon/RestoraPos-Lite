package com.bdtask.restoraposroomdbtab.Interface

import com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter

interface OngoingClickListener {
    fun onGoingItemClick(position: Int, clickedList: ArrayList<Int>, selectedItem: Int)
}