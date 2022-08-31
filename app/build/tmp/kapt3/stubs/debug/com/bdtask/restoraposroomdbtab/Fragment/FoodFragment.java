package com.bdtask.restoraposroomdbtab.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0017H\u0002J\b\u0010$\u001a\u00020\u0017H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Fragment/FoodFragment;", "Landroidx/fragment/app/Fragment;", "()V", "btmBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/BtmsheetItemEditDeleteBinding;", "catgryList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Catgry;", "fCategoryList", "", "foodBinding", "Lcom/bdtask/restoraposroomdbtab/databinding/FragmentFoodBinding;", "foodImage", "spinnerCategory", "startForProfileImageResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "tempAddonsList", "Lcom/bdtask/restoraposroomdbtab/Model/Adn;", "tempVariantList", "Lcom/bdtask/restoraposroomdbtab/Model/Variant;", "addAddonsButtonClick", "", "addFoodBtnClickHandler", "addNewCategoryDialog", "getCategoryLive", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "showCategoryBtmSheet", "variantPlusButtonDialog", "app_debug"})
public final class FoodFragment extends androidx.fragment.app.Fragment {
    private com.bdtask.restoraposroomdbtab.databinding.FragmentFoodBinding foodBinding;
    private com.bdtask.restoraposroomdbtab.databinding.BtmsheetItemEditDeleteBinding btmBinding;
    private java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Catgry> catgryList;
    private java.util.List<java.lang.String> fCategoryList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> tempVariantList;
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.Adn> tempAddonsList;
    private java.lang.String spinnerCategory = "";
    private java.lang.String foodImage = "";
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> startForProfileImageResult = null;
    
    public FoodFragment() {
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
    
    private final void getCategoryLive() {
    }
    
    private final void showCategoryBtmSheet() {
    }
    
    private final void addNewCategoryDialog() {
    }
    
    private final void variantPlusButtonDialog() {
    }
    
    private final void addAddonsButtonClick() {
    }
    
    private final void addFoodBtnClickHandler() {
    }
}