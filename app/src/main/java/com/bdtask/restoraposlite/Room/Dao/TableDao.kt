package com.bdtask.restoraposlite.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.Table

@Dao
interface TableDao {
    @Insert
    suspend fun insertTable(table: Table)

    @Query("SELECT * FROM table_tbl")
    fun getAllTable(): LiveData<List<Table>>

    @Update
    suspend fun updateTable(table: Table)

    @Delete
    suspend fun deleteTable(table: Table)
}