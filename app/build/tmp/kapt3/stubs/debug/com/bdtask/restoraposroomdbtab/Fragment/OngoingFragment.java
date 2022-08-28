package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u00012\u00020\u0002:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0003J&\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/bdtask/restoraposroomdbtab/Interface/OngoingClickListener;", "()V", "ongBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentOngoingBinding;", "position", "", "selectedItem", "status", "", "disableMultiSelect", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onGoingItemClick", "Companion", "app_debug"})
public final class OngoingFragment extends androidx.fragment.app.Fragment implements com.bdtask.restoraposroomdbtab.Interface.OngoingClickListener {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentOngoingBinding ongBinding;
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.Fragment.OngoingFragment.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> ongList;
    @org.jetbrains.annotations.NotNull()
    private static java.util.ArrayList<java.lang.Integer> clickedList;
    private static boolean multiSelect = false;
    private int position = -1;
    private int selectedItem = -1;
    private final java.lang.String status = "Ongoing";
    
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
    private final void disableMultiSelect() {
    }
    
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    @java.lang.Override()
    public void onGoingItemClick(int position, int selectedItem) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OngoingFragment$Companion;", "", "()V", "clickedList", "Ljava/util/ArrayList;", "", "getClickedList", "()Ljava/util/ArrayList;", "setClickedList", "(Ljava/util/ArrayList;)V", "multiSelect", "", "getMultiSelect", "()Z", "setMultiSelect", "(Z)V", "ongList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "getOngList", "()Ljava/util/List;", "setOngList", "(Ljava/util/List;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> getOngList() {
            return null;
        }
        
        public final void setOngList(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order> p0) {
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