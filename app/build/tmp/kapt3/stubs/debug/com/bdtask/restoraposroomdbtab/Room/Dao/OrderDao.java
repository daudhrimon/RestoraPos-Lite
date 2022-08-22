package com.bdtask.restoraposroomdbtab.Room.Dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\'J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J&\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\n\u001a\u00020\u000bH\'J\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Dao/OrderDao;", "", "deleteOnGoing", "", "order", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "(Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOngoing", "Landroidx/lifecycle/LiveData;", "", "status", "", "getTodayOrder", "date", "insertOrder", "", "updateOnGoing", "app_debug"})
public abstract interface OrderDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertOrder(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Order order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM order_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order>> getTodayOrder();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM order_tbl WHERE date LIKE :date AND status LIKE :status")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order>> getTodayOrder(@org.jetbrains.annotations.Nullable()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String status);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM order_tbl WHERE status LIKE :status")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Order>> getOngoing(@org.jetbrains.annotations.NotNull()
    java.lang.String status);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Update()
    public abstract java.lang.Object updateOnGoing(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Order order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Delete()
    public abstract java.lang.Object deleteOnGoing(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Order order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}