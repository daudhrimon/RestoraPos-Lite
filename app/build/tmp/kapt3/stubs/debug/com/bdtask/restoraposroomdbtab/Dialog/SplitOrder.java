package com.bdtask.restoraposroomdbtab.Dialog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u0012\u0010\u001c\u001a\u00020\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\u0010\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\bH\u0017J\b\u0010!\u001a\u00020\u001bH\u0002J\b\u0010\"\u001a\u00020\u001bH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Dialog/SplitOrder;", "Landroid/app/Dialog;", "Lcom/bdtask/restoraposroomdbtab/Interface/SplitFoodClickListener;", "context", "Landroid/content/Context;", "sharedPref", "Lcom/bdtask/restoraposroomdbtab/Util/SharedPref;", "foodCount", "", "(Landroid/content/Context;Lcom/bdtask/restoraposroomdbtab/Util/SharedPref;I)V", "binding", "Lcom/bdtask/restoraposroomdbtab/databinding/DialogSplitOrderBinding;", "dialog", "perAddonPriceList", "Ljava/util/ArrayList;", "", "spiBind", "Lcom/bdtask/restoraposroomdbtab/databinding/DialogSplitItemBinding;", "spinnerList", "splitterCount", "splitterIndex", "splitterList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Split;", "tmpOngItem", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "getCartAndSetPlusFood", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSplitFoodClick", "position", "setSpinnerAdapter", "setSplitterRecycler", "app_debug"})
public final class SplitOrder extends android.app.Dialog implements com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener {
    private final com.bdtask.restoraposroomdbtab.Util.SharedPref sharedPref = null;
    private final int foodCount = 0;
    private com.bdtask.restoraposroomdbtab.Room.Entity.Order tmpOngItem;
    private com.bdtask.restoraposroomdbtab.databinding.DialogSplitOrderBinding binding;
    private android.app.Dialog dialog;
    private com.bdtask.restoraposroomdbtab.databinding.DialogSplitItemBinding spiBind;
    private java.util.ArrayList<java.lang.Integer> spinnerList;
    private int splitterCount = 0;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Split> splitterList;
    private int splitterIndex = 0;
    private java.util.ArrayList<java.lang.Double> perAddonPriceList;
    
    public SplitOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Util.SharedPref sharedPref, int foodCount) {
        super(null);
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void getCartAndSetPlusFood() {
    }
    
    private final void setSplitterRecycler() {
    }
    
    private final void setSpinnerAdapter() {
    }
    
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    @java.lang.Override()
    public void onSplitFoodClick(int position) {
    }
}