package com.bdtask.restoraposroomdbtab.Room;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\f0\u0006H\u0007J\u0018\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u0004H\u0007J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u0004H\u0007J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u0004H\u0007J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u0004H\u0007J\u0018\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u0004H\u0007J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u0012H\u0007J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00140\u0006H\u0007\u00a8\u0006\u0017"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Converters;", "", "()V", "addonListToJson", "", "list", "", "Lcom/bdtask/restoraposroomdbtab/Model/Adn;", "customerInfoToJson", "model", "Lcom/bdtask/restoraposroomdbtab/Model/CsInf;", "foodCartListToJson", "Lcom/bdtask/restoraposroomdbtab/Model/Cart;", "jsonToAddonList", "json", "jsonToCustomerInfo", "jsonToFoodCartList", "jsonToOrderInfo", "Lcom/bdtask/restoraposroomdbtab/Model/OdrInf;", "jsonToVariantList", "Lcom/bdtask/restoraposroomdbtab/Model/Variant;", "orderInfoToJson", "variantListToJson", "app_debug"})
public final class Converters {
    
    public Converters() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.lang.String addonListToJson(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Adn> list) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Adn> jsonToAddonList(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.lang.String variantListToJson(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> list) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> jsonToVariantList(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.lang.String foodCartListToJson(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> list) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> jsonToFoodCartList(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.lang.String orderInfoToJson(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Model.OdrInf model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final com.bdtask.restoraposroomdbtab.Model.OdrInf jsonToOrderInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final java.lang.String customerInfoToJson(@org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Model.CsInf model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.TypeConverter()
    public final com.bdtask.restoraposroomdbtab.Model.CsInf jsonToCustomerInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
}