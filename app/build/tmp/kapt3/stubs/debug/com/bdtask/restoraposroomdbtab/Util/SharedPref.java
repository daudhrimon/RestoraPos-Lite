package com.bdtask.restoraposroomdbtab.Util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\r\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012J\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0012J\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\fJ\u0014\u0010\u0018\u001a\u00020\n2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u0014\u0010\u001a\u001a\u00020\n2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Util/SharedPref;", "", "()V", "NAME", "", "getNAME", "()Ljava/lang/String;", "mSharedPref", "Landroid/content/SharedPreferences;", "SharedPref", "", "getSharedToken", "", "()Ljava/lang/Long;", "init", "context", "Landroid/content/Context;", "readSharedCartList", "", "Lcom/bdtask/restoraposroomdbtab/Model/FoodCart;", "readSharedOrderInfoList", "Lcom/bdtask/restoraposroomdbtab/Model/OrderInfo;", "setSharedToken", "token", "writeSharedCartList", "cartList", "writeSharedOrderInfoList", "orderInfoList", "app_debug"})
public final class SharedPref {
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.Util.SharedPref INSTANCE = null;
    private static android.content.SharedPreferences mSharedPref;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String NAME = "com.bdtask.POS";
    
    private SharedPref() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNAME() {
        return null;
    }
    
    private final void SharedPref() {
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void writeSharedCartList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> cartList) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.FoodCart> readSharedCartList() {
        return null;
    }
    
    public final void writeSharedOrderInfoList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> orderInfoList) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.OrderInfo> readSharedOrderInfoList() {
        return null;
    }
    
    public final void setSharedToken(long token) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSharedToken() {
        return null;
    }
}