package com.bdtask.restoraposroomdbtab.Room.Dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\bH\'J\u0019\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Dao/CategoryDao;", "", "deleteCategory", "", "category", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Category;", "(Lcom/bdtask/restoraposroomdbtab/Room/Entity/Category;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllCategory", "Landroidx/lifecycle/LiveData;", "", "getCategories", "", "insertCategory", "updateCategory", "app_debug"})
public abstract interface CategoryDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert()
    public abstract java.lang.Object insertCategory(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM category_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Category>> getAllCategory();
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Update()
    public abstract java.lang.Object updateCategory(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Delete()
    public abstract java.lang.Object deleteCategory(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT fCategory FROM category_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getCategories();
}