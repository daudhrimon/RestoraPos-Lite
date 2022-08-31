package com.bdtask.restoraposroomdbtab;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.MainActivity.Companion Companion = null;
    public static androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    public static com.google.android.material.navigation.NavigationView navDrawer;
    public static com.bdtask.restoraposroomdbtab.Room.PosDatabase database;
    @org.jetbrains.annotations.NotNull()
    private static java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Food> foodList;
    private static boolean crossBtn = false;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!\u00a8\u0006\""}, d2 = {"Lcom/bdtask/restoraposroomdbtab/MainActivity$Companion;", "", "()V", "crossBtn", "", "getCrossBtn", "()Z", "setCrossBtn", "(Z)V", "database", "Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase;", "getDatabase", "()Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase;", "setDatabase", "(Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase;)V", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "getDrawerLayout", "()Landroidx/drawerlayout/widget/DrawerLayout;", "setDrawerLayout", "(Landroidx/drawerlayout/widget/DrawerLayout;)V", "foodList", "", "Lcom/bdtask/restoraposroomdbtab/Room/Entity/Food;", "getFoodList", "()Ljava/util/List;", "setFoodList", "(Ljava/util/List;)V", "navDrawer", "Lcom/google/android/material/navigation/NavigationView;", "getNavDrawer", "()Lcom/google/android/material/navigation/NavigationView;", "setNavDrawer", "(Lcom/google/android/material/navigation/NavigationView;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.drawerlayout.widget.DrawerLayout getDrawerLayout() {
            return null;
        }
        
        public final void setDrawerLayout(@org.jetbrains.annotations.NotNull()
        androidx.drawerlayout.widget.DrawerLayout p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.android.material.navigation.NavigationView getNavDrawer() {
            return null;
        }
        
        public final void setNavDrawer(@org.jetbrains.annotations.NotNull()
        com.google.android.material.navigation.NavigationView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bdtask.restoraposroomdbtab.Room.PosDatabase getDatabase() {
            return null;
        }
        
        public final void setDatabase(@org.jetbrains.annotations.NotNull()
        com.bdtask.restoraposroomdbtab.Room.PosDatabase p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Food> getFoodList() {
            return null;
        }
        
        public final void setFoodList(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bdtask.restoraposroomdbtab.Room.Entity.Food> p0) {
        }
        
        public final boolean getCrossBtn() {
            return false;
        }
        
        public final void setCrossBtn(boolean p0) {
        }
    }
}