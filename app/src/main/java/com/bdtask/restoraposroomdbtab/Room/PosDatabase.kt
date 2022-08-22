package com.bdtask.restoraposroomdbtab.Room

import android.content.Context
import androidx.room.*
import com.bdtask.restoraposroomdbtab.Room.Dao.*
import com.bdtask.restoraposroomdbtab.Room.Entity.*

@Database(entities = [Category::class, Food::class, Customer::class, DeliveryCompany::class, Waiter::class, Table::class, Order::class], version = 1)
@TypeConverters(Converters::class)
abstract class PosDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun foodDao(): FoodDao

    abstract fun customerDao(): CustomerDao

    abstract fun deliveryCompanyDao(): DeliveryCompanyDao

    abstract fun waiterDao(): WaiterDao

    abstract fun tableDao(): TableDao

    abstract fun orderDao(): OrderDao

// instance of Database
    companion object {
        @Volatile
        private var INSTANCE: PosDatabase? = null
        fun getDatabaseInstance(context: Context): PosDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, PosDatabase::class.java, "restorapos.db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}