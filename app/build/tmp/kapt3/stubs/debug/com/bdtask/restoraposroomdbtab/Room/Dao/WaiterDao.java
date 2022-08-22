package com.bdtask.restoraposroomdbtab.Room.Dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0019\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Dao/WaiterDao;", "", "getAllWaiter", "Landroidx/lifecycle/LiveData;", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Waiter;", "insertWaiter", "", "waiter", "(Lcom/bdtask/restoraposroomdbtab/Room/Entity/Waiter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface WaiterDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert()
    public abstract java.lang.Object insertWaiter(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Room.Entity.Waiter waiter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM waiter_tbl")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Waiter>> getAllWaiter();
}