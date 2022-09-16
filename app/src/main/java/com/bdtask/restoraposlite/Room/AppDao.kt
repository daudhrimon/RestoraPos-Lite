package com.bdtask.restoraposlite.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.*

@Dao
interface AppDao {



    //Category Dao
    @Insert
    suspend fun insertCategory(catgry: Catgry)

    @Query("SELECT * FROM category_tbl")
    fun getAllCategory(): LiveData<List<Catgry>>

    @Update
    suspend fun updateCategory(catgry: Catgry)

    @Query("SELECT fCat FROM category_tbl")
    fun getCategories(): LiveData<List<String>>





    //Food Dao
    @Insert
    suspend fun insertFood(food: Food)

    @Query("SELECT * FROM food_tbl")
    fun getAllFood(): LiveData<List<Food>>

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)





    //Order Dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM order_tbl WHERE dat LIKE :date AND sts LIKE :status")
    fun getTodayOrder(date: String?, status: Int): LiveData<MutableList<Order>>

    @Query("SELECT * FROM order_tbl WHERE sts LIKE :status")
    fun getOngoing(status: Int): LiveData<List<Order>>

    @Query("SELECT * FROM order_tbl WHERE sts LIKE :status")
    fun getCOrder(status: Int): LiveData<List<Order>>

    @Update
    suspend fun updateOrder(order: Order)

    @Query("UPDATE order_tbl SET spl=:status WHERE id = :id")
    suspend fun updateSplitStatus(status: Int, id: Long)

    @Delete
    suspend fun deleteOnGoing(order: Order)





    //Customer Dao
    @Insert()
    suspend fun insertCustomer(cstmr: Cstmr)

    @Query("SELECT * FROM customer_tbl")
    fun getAllCustomer(): LiveData<List<Cstmr>>

    @Update
    suspend fun updateCustomer(cstmr: Cstmr)

    @Delete
    suspend fun deleteCustomer(cstmr: Cstmr)





    // Waiter Dao
    @Insert
    suspend fun insertWaiter(waiter: Waiter)

    @Query("SELECT * FROM waiter_tbl")
    fun getAllWaiter(): LiveData<List<Waiter>>

    @Update
    suspend fun updateWaiter(waiter: Waiter)

    @Delete
    suspend fun deleteWaiter(waiter: Waiter)





    //Table Dao
    @Insert
    suspend fun insertTable(table: Table)

    @Query("SELECT * FROM table_tbl")
    fun getAllTable(): LiveData<List<Table>>

    @Update
    suspend fun updateTable(table: Table)

    @Delete
    suspend fun deleteTable(table: Table)





    //Company Dao
    @Insert
    suspend fun insertDeliveryCompany(deliveryCmpny: Cmpny)

    @Query("SELECT * FROM company")
    fun getDeliveryCompany(): LiveData<List<Cmpny>>

    @Update
    suspend fun updateCompany(cmpny: Cmpny)

    @Delete
    suspend fun deleteCompany(cmpny: Cmpny)

}