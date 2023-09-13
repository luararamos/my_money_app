package com.example.mymoneyapp.wallet.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface StatementDao {
    @Insert
    fun insert(statement: Statement) : Completable

    @Delete
    fun delete(statement: Statement): Completable

    @Update
    fun update(statement: Statement) : Completable

    @Query("SELECT * FROM Statement WHERE type = :type")
    fun getStatement(type: String) : Single<List<Statement>>

}