package com.bdtask.restoraposroomdbtab.Room.Dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\bH\'J\u0019\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Dao/FoodDao;", "", "deleteFood", "", "food", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Food;", "(Lcom/bdtask/restoraposroomdbtab/Room/Entity/Food;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFood", "Landroidx/lifecycle/LiveData;", "", "getFoodTitle", "", "insertFood", "updateFood", "app_debug"})
public abstract interface FoodDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert()
    public abstract java.lang.Object insertFood(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Food food, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM food_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Food>> getAllFood();
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Update()
    public abstract java.lang.Object updateFood(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Food food, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Delete()
    public abstract java.lang.Object deleteFood(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Food food, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT fTitle FROM food_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getFoodTitle();
}