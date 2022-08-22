package com.bdtask.restoraposroomdbtab.Room.Entity;

import java.lang.System;

@androidx.room.Entity(tableName = "food_tbl")
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b\u00a2\u0006\u0002\u0010\rJ\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u00c6\u0003JQ\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\n\u001a\u00020\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0005H\u00d6\u0001R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\u00a8\u0006-"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/Entity/Food;", "", "id", "", "fCategory", "", "fTitle", "fVariant", "", "Lcom/bdtask/restoraposroomdbtab/Model/Variant;", "fImage", "fAddons", "Lcom/bdtask/restoraposroomdbtab/Model/Addon;", "(JLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/util/List;)V", "getFAddons", "()Ljava/util/List;", "setFAddons", "(Ljava/util/List;)V", "getFCategory", "()Ljava/lang/String;", "setFCategory", "(Ljava/lang/String;)V", "getFImage", "setFImage", "getFTitle", "setFTitle", "getFVariant", "setFVariant", "getId", "()J", "setId", "(J)V", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class Food {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private long id;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fCategory;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fTitle;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> fVariant;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fImage;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> fAddons;
    
    @org.jetbrains.annotations.NotNull()
    public final com.bdtask.restoraposroomdbtab.Room.Entity.Food copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String fCategory, @org.jetbrains.annotations.NotNull()
    java.lang.String fTitle, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> fVariant, @org.jetbrains.annotations.NotNull()
    java.lang.String fImage, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> fAddons) {
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
    
    public Food(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String fCategory, @org.jetbrains.annotations.NotNull()
    java.lang.String fTitle, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> fVariant, @org.jetbrains.annotations.NotNull()
    java.lang.String fImage, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> fAddons) {
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
    public final java.lang.String getFCategory() {
        return null;
    }
    
    public final void setFCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFTitle() {
        return null;
    }
    
    public final void setFTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> getFVariant() {
        return null;
    }
    
    public final void setFVariant(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFImage() {
        return null;
    }
    
    public final void setFImage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> getFAddons() {
        return null;
    }
    
    public final void setFAddons(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Addon> p0) {
    }
}