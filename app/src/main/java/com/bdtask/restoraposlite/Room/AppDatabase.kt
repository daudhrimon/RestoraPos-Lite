package com.bdtask.restoraposlite.room

import android.content.Context
import androidx.room.*
import com.bdtask.restoraposlite.room.entity.*

@Database(
    entities =
    [Category::class,
        Food::class,
        Customer::class,
        Company::class,
        Waiter::class,
        Table::class,
        Order::class,
        Split::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun AppDao(): AppDao

    //Singleton instance of Database
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room
                        .databaseBuilder(context, AppDatabase::class.java, "restorapos.db")
                        /*.createFromAsset("database/demo.db")
                        .fallbackToDestructiveMigration()*/
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}