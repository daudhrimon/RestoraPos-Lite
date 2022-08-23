package com.bdtask.restoraposroomdbtab.Room.Entity;

import java.lang.System;

@androidx.room.Entity(tableName = "order_tbl")
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\u0002\u0010\rJ\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u00c6\u0003JQ\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0005H\u00d6\u0001R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015\u00a8\u0006-"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "", "id", "", "status", "", "date", "token", "cartList", "", "Lcom/bdtask/restoraposroomdbtab/Model/FoodCart;", "orderInfoList", "Lcom/bdtask/restoraposroomdbtab/Model/OrderInfo;", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getCartList", "()Ljava/util/List;", "setCartList", "(Ljava/util/List;)V", "getDate", "()Ljava/lang/String;", "setDate", "(Ljava/lang/String;)V", "getId", "()J", "setId", "(J)V", "getOrderInfoList", "setOrderInfoList", "getStatus", "setStatus", "getToken", "setToken", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class Order {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private long id;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String status;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String date;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String token;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> cartList;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> orderInfoList;
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Room.Entity.Order copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> cartList, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> orderInfoList) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Order(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> cartList, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> orderInfoList) {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final void setId(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDate() {
        return null;
    }
    
    public final void setDate(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToken() {
        return null;
    }
    
    public final void setToken(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> getCartList() {
        return null;
    }
    
    public final void setCartList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> getOrderInfoList() {
        return null;
    }
    
    public final void setOrderInfoList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> p0) {
    }
}