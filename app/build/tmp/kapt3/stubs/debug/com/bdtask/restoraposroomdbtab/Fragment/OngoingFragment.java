package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00192\u00020\u00012\u00020\u0002:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u000eH\u0003J&\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "()V", "foodCount", "", "ongBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentOngoingBinding;", "ongList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "ongPos", "selectedItem", "disableMultiSelect", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onGoingItemClick", "position", "Companion", "app_debug"})
public final class OngoingFragment extends androidx.fragment.app.Fragment implements com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding ongBinding;
    private int ongPos = -1;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> ongList;
    private int selectedItem = -1;
    private int foodCount = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.ArrayList<java.lang.Integer> clickedList;
    private static boolean multiSelect = false;
    
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
    
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    @java.lang.Override()
    public void onGoingItemClick(int position, int selectedItem) {
    }
    
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    private final void disableMultiSelect() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment$Companion;", "", "()V", "clickedList", "Ljava/util/ArrayList;", "", "getClickedList", "()Ljava/util/ArrayList;", "setClickedList", "(Ljava/util/ArrayList;)V", "multiSelect", "", "getMultiSelect", "()Z", "setMultiSelect", "(Z)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
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
    }
}