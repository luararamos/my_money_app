package com.example.mymoneyapp.wallet.data

import android.content.Context
import com.example.mymoneyapp.wallet.db.AppDatabase
import com.example.mymoneyapp.wallet.db.Statement
import io.reactivex.Completable
import io.reactivex.Single

class WalletRepository( context : Context) {
    private val dao = AppDatabase.getDatabase(context).statementDao()

    fun insertStatement(statement: Statement): Completable {
        return dao.insert(statement)
    }

    fun deleteStatement(statementId: Int): Completable {

        return dao.delete(statementId)
    }
    fun findStatements(type: String): Single<List<Statement>> {
        return dao.getStatement(type)
    }

    fun findAllStatement(): Single<List<Statement>>{
        return  dao.getAllStatement()
    }

    fun accountBalance(): Single<Double>{
        return dao.getSumEarn()
    }
}