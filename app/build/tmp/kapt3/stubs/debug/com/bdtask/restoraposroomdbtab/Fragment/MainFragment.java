package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u001fH\u0016J&\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0016J=\u0010)\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u00152\b\u0010+\u001a\u0004\u0018\u00010\t2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000-H\u0016\u00a2\u0006\u0002\u00101J\b\u00102\u001a\u00020\u001fH\u0002J\u0010\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u00020\u001fH\u0002J\b\u00107\u001a\u00020\u001fH\u0002J\b\u00108\u001a\u00020\u001fH\u0002J\u0016\u00109\u001a\u00020\t2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/MainFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/bdtask/restoraposroomdbtab/Interface/FoodClickListener;", "Lcom/bdtask/restoraposroomdbtab/Interface/CartClickListener;", "()V", "cartList", "", "Lcom/bdtask/restoraposroomdbtab/Model/FoodCart;", "categoryList", "", "foodPrice", "", "foodQuantity", "", "foodVariant", "grandTotal", "homeAddonList", "Lcom/bdtask/restoraposroomdbtab/Model/HomeAddon;", "mainBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentMainBinding;", "orderId", "", "Ljava/lang/Long;", "printHelper", "Lcom/bdtask/restoraposroomdbtab/Printer/PrinterUtil/SunmiPrintHelper;", "sharedPref", "Lcom/bdtask/restoraposroomdbtab/Util/SharedPref;", "totalUnitPrice", "variantNameList", "variantPrice", "initial", "", "onCartReload", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onFoodClick", "foodId", "foodTitle", "variantlist", "", "Lcom/bdtask/restoraposroomdbtab/Model/Variant;", "addonList", "Lcom/bdtask/restoraposroomdbtab/Model/Addon;", "(Ljava/lang/Long;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "placeOrderClickHandler", "printToken", "printDialog", "Lcn/pedant/SweetAlert/SweetAlertDialog;", "quickOrderClickHandler", "setCartRecyclerAdapter", "showTokenPrintDialog", "tokenLoopData", "list", "app_debug"})
public final class MainFragment extends androidx.fragment.app.Fragment implements com.bdtask.restoraposroomdbtab.Interface.FoodClickListener, com.bdtask.restoraposroomdbtab.Interface.CartClickListener {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentMainBinding mainBinding;
    private java.util.List<java.lang.String> categoryList;
    private java.util.List<java.lang.String> variantNameList;
    private double grandTotal = 0.0;
    private java.lang.String foodVariant = "";
    private double variantPrice = 0.0;
    private int foodQuantity = 1;
    private double foodPrice = 0.0;
    private double totalUnitPrice = 0.0;
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> homeAddonList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> cartList;
    private com.bdtask.restoraposroomdbtab.Util.SharedPref sharedPref;
    private com.bdtask.restoraposroomdbtab.Printer.PrinterUtil.SunmiPrintHelper printHelper;
    private java.lang.Long orderId;
    
    public MainFragment() {
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
    
    private final void quickOrderClickHandler() {
    }
    
    private final void placeOrderClickHandler() {
    }
    
    private final void showTokenPrintDialog() {
    }
    
    private final void printToken(cn.pedant.SweetAlert.SweetAlertDialog printDialog) {
    }
    
    private final java.lang.String tokenLoopData(java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> list) {
        return null;
    }
    
    @java.lang.Override()
    public void onFoodClick(@org.jetbrains.annotations.Nullable()
    java.lang.Long foodId, @org.jetbrains.annotations.Nullable()
    java.lang.String foodTitle, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> variantlist, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> addonList) {
    }
    
    private final void setCartRecyclerAdapter() {
    }
    
    @java.lang.Override()
    public void onCartReload() {
    }
    
    private final void initial() {
    }
}