package com.bdtask.restoraposroomdbtab.Room.Entity;

import java.lang.System;

@androidx.room.Entity(tableName = "order_tbl")
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\u0002\u0010\u0013J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\tH\u00c6\u0003J\t\u0010*\u001a\u00020\tH\u00c6\u0003J\t\u0010+\u001a\u00020\fH\u00c6\u0003J\t\u0010,\u001a\u00020\fH\u00c6\u0003J\t\u0010-\u001a\u00020\u000fH\u00c6\u0003Js\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0001J\u0013\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00102\u001a\u00020\u0005H\u00d6\u0001J\t\u00103\u001a\u00020\tH\u00d6\u0001R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017\u00a8\u00064"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Entity/Order;", "", "id", "", "sts", "", "spl", "mrg", "dat", "", "tkn", "vat", "", "crg", "odrInf", "Lcom/bdtask/restoraposroomdbtab/Model/OdrInf;", "cart", "", "Lcom/bdtask/restoraposroomdbtab/Model/Cart;", "(JIIILjava/lang/String;Ljava/lang/String;DDLcom/bdtask/restoraposroomdbtab/Model/OdrInf;Ljava/util/List;)V", "getCart", "()Ljava/util/List;", "getCrg", "()D", "getDat", "()Ljava/lang/String;", "getId", "()J", "getMrg", "()I", "getOdrInf", "()Lcom/bdtask/restoraposroomdbtab/Model/OdrInf;", "getSpl", "getSts", "getTkn", "getVat", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Order {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final int sts = 0;
    private final int spl = 0;
    private final int mrg = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tkn = null;
    private final double vat = 0.0;
    private final double crg = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final com.bdtask.restoraposroomdbtab.Model.OdrInf odrInf = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> cart = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Room.Entity.Order copy(long id, int sts, int spl, int mrg, @org.jetbrains.annotations.NotNull()
    java.lang.String dat, @org.jetbrains.annotations.NotNull()
    java.lang.String tkn, double vat, double crg, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Model.OdrInf odrInf, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> cart) {
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
    
    public Order(long id, int sts, int spl, int mrg, @org.jetbrains.annotations.NotNull()
    java.lang.String dat, @org.jetbrains.annotations.NotNull()
    java.lang.String tkn, double vat, double crg, @org.jetbrains.annotations.NotNull()
    com.bdtask.restoraposroomdbtab.Model.OdrInf odrInf, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> cart) {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getSts() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getSpl() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getMrg() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTkn() {
        return null;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final double getVat() {
        return 0.0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final double getCrg() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Model.OdrInf component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Model.OdrInf getOdrInf() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Cart> getCart() {
        return null;
    }
}