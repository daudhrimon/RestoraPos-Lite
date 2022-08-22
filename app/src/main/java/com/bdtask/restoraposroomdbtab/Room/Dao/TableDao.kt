package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bdtask.restoraposroomdbtab.Room.Entity.Table

@Dao
interface TableDao {
    @Insert
    suspend fun insertTable(table: Table)

    @Query("SELECT * FROM table_tbl")
    fun getAllTable(): LiveData<List<Table>>
}