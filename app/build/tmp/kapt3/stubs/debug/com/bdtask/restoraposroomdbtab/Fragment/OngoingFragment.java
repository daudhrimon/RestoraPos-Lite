package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0003J&\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J<\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u00060\u0011R\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00140\u0016j\b\u0012\u0004\u0012\u00020\u0014`\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "()V", "ongBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentOngoingBinding;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onGoingItemClick", "", "holder", "Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter$VHOngoing;", "Lcom/bdtask/restoraposroomdbtab/Adapter/OngoingAdapter;", "position", "", "clickedList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "multiSelect", "", "Companion", "app_debug"})
public final class OngoingFragment extends androidx.fragment.app.Fragment implements com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding ongBinding;
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> ongoingList;
    
    public OngoingFragment() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"NewApi"})
    @java.lang.Override()
    public void onGoingItemClick(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Adapter.OngoingAdapter.VHOngoing holder, int position, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.Integer> clickedList, boolean multiSelect) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment$Companion;", "", "()V", "ongoingList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "getOngoingList", "()Ljava/util/List;", "setOngoingList", "(Ljava/util/List;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> getOngoingList() {
            return null;
        }
        
        public final void setOngoingList(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> p0) {
        }
    }
}