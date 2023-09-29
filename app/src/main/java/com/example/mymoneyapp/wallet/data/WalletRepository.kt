package com.example.mymoneyapp.wallet.data

import android.content.Context
import com.example.mymoneyapp.wallet.db.AppDatabase
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import io.reactivex.Completable
import io.reactivex.Single

class WalletRepository(context: Context) {
    private val dao = AppDatabase.getDatabase(context).statementDao()
    private val daoUser = AppDatabase.getDatabase(context).userDao()

    fun updateUser(user: User): Completable {
        return daoUser.updateUser(user)

    }

    fun insertUser(user: User): Completable {
        return daoUser.insertUser(user)

    }

    fun insertStatement(statement: Statement): Completable {
        return dao.insert(statement)
    }
    fun findAllUser(): Single<List<User>> {
        return daoUser.getAllUser()
    }

    //    fun findUser():Single<String>{
//        return daoUser.getUser()
//    }


    fun deleteStatement(statementId: Int): Completable {
        return dao.delete(statementId)
    }

    fun findStatements(type: String): Single<List<Statement>> {
        return dao.getStatement(type)
    }

    fun findAllStatement(): Single<List<Statement>> {
        return dao.getAllStatement()
    }

    fun accountBalance(): Single<Double> {
        return dao.getEarnMinusSpend()
    }

    fun accountBalanceError(type: String): Single<Double> {
        return dao.getSum(type)
    }
}
