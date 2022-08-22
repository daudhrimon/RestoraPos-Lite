package com.bdtask.restoraposroomdbtab.Adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0015B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u001c\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Adapter/CategoryAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/bdtask/restoraposroomdbtab/Adapter/CategoryAdapter$CategoryVH;", "context", "Landroid/content/Context;", "cateList", "", "", "foodClickListener", "Lcom/bdtask/restoraposroomdbtab/Interface/FoodClickListener;", "(Landroid/content/Context;Ljava/util/List;Lcom/bdtask/restoraposroomdbtab/Interface/FoodClickListener;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "CategoryVH", "app_debug"})
public final class CategoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.bdtask.restoraposroomdbtab.Adapter.CategoryAdapter.CategoryVH> {
    private final android.content.Context context = null;
    private java.util.List<java.lang.String> cateList;
    private com.bdtask.restoraposroomdbtab.Interface.FoodClickListener foodClickListener;
    
    public CategoryAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> cateList, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Interface.FoodClickListener foodClickListener) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.bdtask.restoraposroomdbtab.Adapter.CategoryAdapter.CategoryVH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Adapter.CategoryAdapter.CategoryVH holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Adapter/CategoryAdapter$CategoryVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/bdtask/restoraposroomdbtab/databinding/VhFoodRecyclerBinding;", "(Lcom/bdtask/restoraposroomdbtab/Adapter/CategoryAdapter;Lcom/bdtask/restoraposroomdbtab/databinding/VhFoodRecyclerBinding;)V", "getBinding", "()Lcom/bdtask/restoraposroomdbtab/databinding/VhFoodRecyclerBinding;", "setBinding", "(Lcom/bdtask/restoraposroomdbtab/databinding/VhFoodRecyclerBinding;)V", "app_debug"})
    public final class CategoryVH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding binding;
        
        public CategoryVH(@org.jetbrains.annotations.NotNull()
        com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding getBinding() {
            return null;
        }
        
        public final void setBinding(@org.jetbrains.annotations.NotNull()
        com.bdtask.restoraposroomdbtab.databinding.VhFoodRecyclerBinding p0) {
        }
    }
}