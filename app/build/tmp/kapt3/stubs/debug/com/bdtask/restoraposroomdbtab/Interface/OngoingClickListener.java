package com.bdtask.restoraposroomdbtab.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u00060\u0005R\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "", "onGoingItemClick", "", "holder", "Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter$VHOngoing;", "Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter;", "position", "", "clickedList", "", "app_debug"})
public abstract interface OngoingClickListener {
    
    public abstract void onGoingItemClick(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter.VHOngoing holder, int position, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> clickedList);
}