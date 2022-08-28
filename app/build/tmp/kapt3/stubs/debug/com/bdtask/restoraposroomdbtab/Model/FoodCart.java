package com.bdtask.restoraposroomdbtab.Model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\'\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0010J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0006H\u00c6\u0003J\t\u0010.\u001a\u00020\bH\u00c6\u0003J\t\u0010/\u001a\u00020\u0006H\u00c6\u0003J\t\u00100\u001a\u00020\u0006H\u00c6\u0003J\t\u00101\u001a\u00020\u0006H\u00c6\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003Ji\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u00020\bH\u00d6\u0001J\t\u00109\u001a\u00020\u0003H\u00d6\u0001R \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\t\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\u000f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"R\u001a\u0010\n\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0016\"\u0004\b(\u0010\u0018R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0016\"\u0004\b*\u0010\u0018\u00a8\u0006:"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Model/FoodCart;", "", "foodTitle", "", "foodVariant", "variantPrice", "", "foodQuantity", "", "foodPrice", "totalUnitPrice", "addonsPrice", "addonList", "", "Lcom/bdtask/restoraposroomdbtab/Model/HomeAddon;", "note", "(Ljava/lang/String;Ljava/lang/String;DIDDDLjava/util/List;Ljava/lang/String;)V", "getAddonList", "()Ljava/util/List;", "setAddonList", "(Ljava/util/List;)V", "getAddonsPrice", "()D", "setAddonsPrice", "(D)V", "getFoodPrice", "setFoodPrice", "getFoodQuantity", "()I", "setFoodQuantity", "(I)V", "getFoodTitle", "()Ljava/lang/String;", "setFoodTitle", "(Ljava/lang/String;)V", "getFoodVariant", "setFoodVariant", "getNote", "setNote", "getTotalUnitPrice", "setTotalUnitPrice", "getVariantPrice", "setVariantPrice", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class FoodCart {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String foodTitle;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String foodVariant;
    private double variantPrice;
    private int foodQuantity;
    private double foodPrice;
    private double totalUnitPrice;
    private double addonsPrice;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> addonList;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String note;
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Model.FoodCart copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String foodVariant, double variantPrice, int foodQuantity, double foodPrice, double totalUnitPrice, double addonsPrice, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> addonList, @org.jetbrains.annotations.NotNull()
    java.lang.String note) {
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
    
    public FoodCart(@org.jetbrains.annotations.NotNull()
    java.lang.String foodTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String foodVariant, double variantPrice, int foodQuantity, double foodPrice, double totalUnitPrice, double addonsPrice, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> addonList, @org.jetbrains.annotations.NotNull()
    java.lang.String note) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodTitle() {
        return null;
    }
    
    public final void setFoodTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodVariant() {
        return null;
    }
    
    public final void setFoodVariant(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double getVariantPrice() {
        return 0.0;
    }
    
    public final void setVariantPrice(double p0) {
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getFoodQuantity() {
        return 0;
    }
    
    public final void setFoodQuantity(int p0) {
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double getFoodPrice() {
        return 0.0;
    }
    
    public final void setFoodPrice(double p0) {
    }
    
    public final double component6() {
        return 0.0;
    }
    
    public final double getTotalUnitPrice() {
        return 0.0;
    }
    
    public final void setTotalUnitPrice(double p0) {
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final double getAddonsPrice() {
        return 0.0;
    }
    
    public final void setAddonsPrice(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> getAddonList() {
        return null;
    }
    
    public final void setAddonList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.HomeAddon> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNote() {
        return null;
    }
    
    public final void setNote(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
}