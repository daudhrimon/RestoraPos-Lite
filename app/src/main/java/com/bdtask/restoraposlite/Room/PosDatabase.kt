package com.bdtask.restoraposlite.Room

import android.content.Context
import androidx.room.*
import com.bdtask.restoraposlite.Room.Dao.*
import com.bdtask.restoraposlite.Room.Entity.*

@Database(entities = [Catgry::class, Food::class, Cstmr::class, Cmpny::class, Waiter::class, Table::class, Order::class, Split::class], version = 1)
@TypeConverters(Converters::class)
abstract class PosDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun foodDao(): FoodDao

    abstract fun customerDao(): CustomerDao

    abstract fun deliveryCompanyDao(): CompanyDao

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