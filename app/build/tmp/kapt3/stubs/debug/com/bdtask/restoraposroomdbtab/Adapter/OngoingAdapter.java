package com.bdtask.restoraposroomdbtab.Adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001-BC\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\b\u0010$\u001a\u00020\u0014H\u0016J\u001c\u0010%\u001a\u00020&2\n\u0010\'\u001a\u00060\u0002R\u00020\u00002\u0006\u0010(\u001a\u00020\u0014H\u0016J\u001c\u0010)\u001a\u00060\u0002R\u00020\u00002\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0014H\u0016R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter$VHOngoing;", "context", "Landroid/content/Context;", "orderList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "ongoingClickListener", "Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "ongHeader", "Landroid/widget/TextView;", "searchBtn", "Landroid/widget/ImageView;", "crossBtn", "scrlView", "Landroid/widget/HorizontalScrollView;", "(Landroid/content/Context;Ljava/util/List;Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;Landroid/widget/TextView;Landroid/widget/ImageView;Landroid/widget/ImageView;Landroid/widget/HorizontalScrollView;)V", "clickedList", "Ljava/util/ArrayList;", "", "getClickedList", "()Ljava/util/ArrayList;", "setClickedList", "(Ljava/util/ArrayList;)V", "index", "getIndex", "()I", "setIndex", "(I)V", "multiSelect", "", "getMultiSelect", "()Z", "setMultiSelect", "(Z)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "VHOngoing", "app_debug"})
public final class OngoingAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter.VHOngoing> {
    private final android.content.Context context = null;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> orderList;
    private final com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener ongoingClickListener = null;
    private final android.widget.TextView ongHeader = null;
    private final android.widget.ImageView searchBtn = null;
    private final android.widget.ImageView crossBtn = null;
    private final android.widget.HorizontalScrollView scrlView = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<java.lang.Integer> clickedList;
    private boolean multiSelect = false;
    private int index = -1;
    
    public OngoingAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> orderList, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener ongoingClickListener, @org.jetbrains.annotations.NotNull()
    android.widget.TextView ongHeader, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView searchBtn, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView crossBtn, @org.jetbrains.annotations.NotNull()
    android.widget.HorizontalScrollView scrlView) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.lang.Integer> getClickedList() {
        return null;
    }
    
    public final void setClickedList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.Integer> p0) {
    }
    
    public final boolean getMultiSelect() {
        return false;
    }
    
    public final void setMultiSelect(boolean p0) {
    }
    
    public final int getIndex() {
        return 0;
    }
    
    public final void setIndex(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter.VHOngoing onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter.VHOngoing holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter$VHOngoing;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/bdtask/restoraposroomdbtab/databinding/VhOngoingBinding;", "(Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter;Lcom/bdtask/restoraposroomdbtab/databinding/VhOngoingBinding;)V", "getBinding", "()Lcom/bdtask/restoraposroomdbtab/databinding/VhOngoingBinding;", "setBinding", "(Lcom/bdtask/restoraposroomdbtab/databinding/VhOngoingBinding;)V", "app_debug"})
    public final class VHOngoing extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding binding;
        
        public VHOngoing(@org.jetbrains.annotations.NotNull()
        com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding getBinding() {
            return null;
        }
        
        public final void setBinding(@org.jetbrains.annotations.NotNull()
        com.bdtask.restoraposroomdbtab.databinding.VhOngoingBinding p0) {
        }
    }
}