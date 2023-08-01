package com.example.mymoneyapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface StatementDao {
    @Insert
    fun insert(statement: Statement)

    @Delete
    fun delete(statement: Statement): Int

    @Update
    fun update(statement: Statement)

}