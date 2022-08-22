package com.bdtask.restoraposroomdbtab.Room;

import java.lang.System;

@androidx.room.TypeConverters(value = {com.bdtask.restoraposroomdbtab.Room.Converters.class})
@androidx.room.Database(entities = {com.bdtask.restoraposroomdbtab.Room.Entity.Category.class, com.bdtask.restoraposroomdbtab.Room.Entity.Food.class, com.bdtask.restoraposroomdbtab.Room.Entity.Customer.class, com.bdtask.restoraposroomdbtab.Room.Entity.DeliveryCompany.class, com.bdtask.restoraposroomdbtab.Room.Entity.Waiter.class, com.bdtask.restoraposroomdbtab.Room.Entity.Table.class, com.bdtask.restoraposroomdbtab.Room.Entity.Order.class}, version = 1)
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&\u00a8\u0006\u0012"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase;", "Landroidx/room/RoomDatabase;", "()V", "categoryDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/CategoryDao;", "customerDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/CustomerDao;", "deliveryCompanyDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/DeliveryCompanyDao;", "foodDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/FoodDao;", "orderDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/OrderDao;", "tableDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/TableDao;", "waiterDao", "Lcom/bdtask/restoraposroomdbtab/Room/Dao/WaiterDao;", "Companion", "app_debug"})
public abstract class PosDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final com.bdtask.restoraposroomdbtab.Room.PosDatabase.Companion Companion = null;
    @kotlin.jvm.Volatile()
    private static volatile com.bdtask.restoraposroomdbtab.Room.PosDatabase INSTANCE;
    
    public PosDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.FoodDao foodDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.CustomerDao customerDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.DeliveryCompanyDao deliveryCompanyDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.WaiterDao waiterDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.TableDao tableDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bdtask.restoraposroomdbtab.Room.Dao.OrderDao orderDao();
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/bdtask/restoraposroomdbtab/Room/PosDatabase;", "getDatabaseInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bdtask.restoraposroomdbtab.Room.PosDatabase getDatabaseInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}