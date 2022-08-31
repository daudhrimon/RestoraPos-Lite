package com.bdtask.restoraposroomdbtab.Dialog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0012\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\bH\u0016J\b\u0010%\u001a\u00020\u001fH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Dialog/SplitOrder;", "Landroid/app/Dialog;", "Lcom/bdtask/restoraposroomdbtab/Interface/SplitFoodClickListener;", "context", "Landroid/content/Context;", "ongItem", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "foodCount", "", "(Landroid/content/Context;Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;I)V", "binding", "Lcom/bdtask/restoraposroomdbtab/databinding/DialogSplitOrderBinding;", "dialog", "getOngItem", "()Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "setOngItem", "(Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;)V", "spiBind", "Lcom/bdtask/restoraposroomdbtab/databinding/DialogSplitItemBinding;", "spinnerList", "Ljava/util/ArrayList;", "splitAdnPrice", "", "splitterCount", "splitterIndex", "splitterList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Split;", "tmpOngItem", "totalAdnPrice", "getInit", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSplitFoodClick", "position", "setSplitterRecycler", "app_debug"})
public final class SplitOrder extends android.app.Dialog implements com.bdtask.restoraposroomdbtab.Interface.SplitFoodClickListener {
    @org.jetbrains.annotations.NotNull()
    private com.bdtask.restoraposroomdbtab.Room.Entity.Order ongItem;
    private final int foodCount = 0;
    private final com.bdtask.restoraposroomdbtab.Room.Entity.Order tmpOngItem = null;
    private com.bdtask.restoraposroomdbtab.databinding.DialogSplitOrderBinding binding;
    private android.app.Dialog dialog;
    private com.bdtask.restoraposroomdbtab.databinding.DialogSplitItemBinding spiBind;
    private java.util.ArrayList<java.lang.Integer> spinnerList;
    private int splitterCount = 0;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Split> splitterList;
    private double totalAdnPrice = 0.0;
    private double splitAdnPrice = 0.0;
    private int splitterIndex = 0;
    
    public SplitOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Order ongItem, int foodCount) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Room.Entity.Order getOngItem() {
        return null;
    }
    
    public final void setOngItem(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Order p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setSplitterRecycler() {
    }
    
    private final void getInit() {
    }
    
    @java.lang.Override()
    public void onSplitFoodClick(int position) {
    }
}