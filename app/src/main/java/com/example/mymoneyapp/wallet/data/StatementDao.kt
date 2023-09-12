package com.example.mymoneyapp.wallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.mymoneyapp.wallet.data.Statement

@Dao
interface StatementDao {
    @Insert
    fun insert(statement: Statement)

    @Delete
    fun delete(statement: Statement): Int

    @Update
    fun update(statement: Statement)

}