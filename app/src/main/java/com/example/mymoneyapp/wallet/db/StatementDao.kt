package com.example.mymoneyapp.wallet.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface StatementDao {
    @Insert
    fun insert(statement: Statement): Completable

    @Query("DELETE FROM Statement WHERE id = :statementId")
    fun delete(statementId: Int): Completable

    @Query("SELECT * FROM Statement WHERE type = :type")
    fun getStatement(type: String): Single<List<Statement>>

    @Query("SELECT * FROM Statement")
    fun getAllStatement(): Single<List<Statement>>

    @Query("SELECT (SELECT SUM(money) FROM Statement WHERE type = 'earn')- (SELECT SUM(money) FROM Statement WHERE type = 'spend')")
    fun getEarnMinusSpend(): Single<Double>

    @Query("SELECT SUM(money) FROM Statement WHERE type = :type")
    fun getSum(type: String): Single<Double>


}