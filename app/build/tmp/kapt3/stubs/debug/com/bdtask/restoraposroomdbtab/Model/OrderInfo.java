package com.bdtask.restoraposroomdbtab.Model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000bJ\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003JK\u0010\"\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\u0006H\u00d6\u0001R \u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\t\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0013R\u001a\u0010\n\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0011\"\u0004\b\u0017\u0010\u0013R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0011\"\u0004\b\u001b\u0010\u0013\u00a8\u0006)"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Model/OrderInfo;", "", "customerInfo", "", "Lcom/bdtask/restoraposroomdbtab/Model/CustomerInfo;", "customerType", "", "waiter", "table", "deliveryCompany", "orderIdTp", "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCustomerInfo", "()Ljava/util/List;", "setCustomerInfo", "(Ljava/util/List;)V", "getCustomerType", "()Ljava/lang/String;", "setCustomerType", "(Ljava/lang/String;)V", "getDeliveryCompany", "setDeliveryCompany", "getOrderIdTp", "setOrderIdTp", "getTable", "setTable", "getWaiter", "setWaiter", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class OrderInfo {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> customerInfo;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String customerType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String waiter;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String table;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String deliveryCompany;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String orderIdTp;
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Model.OrderInfo copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> customerInfo, @org.jetbrains.annotations.NotNull()
    java.lang.String customerType, @org.jetbrains.annotations.NotNull()
    java.lang.String waiter, @org.jetbrains.annotations.NotNull()
    java.lang.String table, @org.jetbrains.annotations.NotNull()
    java.lang.String deliveryCompany, @org.jetbrains.annotations.NotNull()
    java.lang.String orderIdTp) {
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
    
    public OrderInfo(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> customerInfo, @org.jetbrains.annotations.NotNull()
    java.lang.String customerType, @org.jetbrains.annotations.NotNull()
    java.lang.String waiter, @org.jetbrains.annotations.NotNull()
    java.lang.String table, @org.jetbrains.annotations.NotNull()
    java.lang.String deliveryCompany, @org.jetbrains.annotations.NotNull()
    java.lang.String orderIdTp) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> getCustomerInfo() {
        return null;
    }
    
    public final void setCustomerInfo(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.CustomerInfo> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCustomerType() {
        return null;
    }
    
    public final void setCustomerType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWaiter() {
        return null;
    }
    
    public final void setWaiter(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTable() {
        return null;
    }
    
    public final void setTable(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeliveryCompany() {
        return null;
    }
    
    public final void setDeliveryCompany(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOrderIdTp() {
        return null;
    }
    
    public final void setOrderIdTp(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
}