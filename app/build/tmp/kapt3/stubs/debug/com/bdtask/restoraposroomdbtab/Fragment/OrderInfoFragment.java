package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u001fH\u0002J\b\u0010!\u001a\u00020\u001fH\u0002J&\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\b\u0010*\u001a\u00020\u001fH\u0002J\b\u0010+\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/OrderInfoFragment;", "Landroidx/fragment/app/Fragment;", "()V", "binding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentOrderInfoBinding;", "cusInfoList", "", "Lcom/bdtask/restoraposroomdbtab/Model/CsInf;", "cusNameList", "", "cusTypeList", "Ljava/util/ArrayList;", "deliveryCmpnyList", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Cmpny;", "deliveryCompanySpnrList", "odrInf", "Lcom/bdtask/restoraposroomdbtab/Model/OdrInf;", "selectedCsInf", "selectedCustomerType", "selectedDeliveryCompany", "selectedTable", "selectedWaiter", "sharedPref", "Lcom/bdtask/restoraposroomdbtab/Util/SharedPref;", "tableList", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Table;", "tableSpnrList", "waiterList", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Waiter;", "waiterSpnrList", "addNewCustomer", "", "deliveryCompanyAddBtnClick", "doneButtonClickHandler", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "tableAddBtnClick", "waiterAddBtnClick", "app_debug"})
public final class OrderInfoFragment extends androidx.fragment.app.Fragment {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentOrderInfoBinding binding;
    private java.util.List<java.lang.String> cusNameList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.CsInf> cusInfoList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Cmpny> deliveryCmpnyList;
    private java.util.List<java.lang.String> deliveryCompanySpnrList;
    private java.util.ArrayList<java.lang.String> cusTypeList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Waiter> waiterList;
    private java.util.List<java.lang.String> waiterSpnrList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Table> tableList;
    private java.util.List<java.lang.String> tableSpnrList;
    private com.bdtask.restoraposroomdbtab.Model.CsInf selectedCsInf;
    private java.lang.String selectedCustomerType = "";
    private java.lang.String selectedWaiter = "";
    private java.lang.String selectedTable = "";
    private java.lang.String selectedDeliveryCompany = "";
    private com.bdtask.restoraposroomdbtab.Model.OdrInf odrInf;
    private com.bdtask.restoraposroomdbtab.Util.SharedPref sharedPref;
    
    public OrderInfoFragment() {
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
    
    private final void doneButtonClickHandler() {
    }
    
    private final void tableAddBtnClick() {
    }
    
    private final void waiterAddBtnClick() {
    }
    
    private final void deliveryCompanyAddBtnClick() {
    }
    
    private final void addNewCustomer() {
    }
}