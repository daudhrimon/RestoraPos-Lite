package com.bdtask.restoraposroomdbtab.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J0\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0007j\b\u0012\u0004\u0012\u00020\u0005`\b2\u0006\u0010\t\u001a\u00020\u0005H&\u00a8\u0006\n"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "", "onGoingItemClick", "", "position", "", "clickedList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "selectedItem", "app_debug"})
public abstract interface OngoingClickListener {
    
    public abstract void onGoingItemClick(int position, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.Integer> clickedList, int selectedItem);
}