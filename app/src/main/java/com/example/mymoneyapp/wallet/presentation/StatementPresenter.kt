package com.example.mymoneyapp.wallet.presentation

import android.annotation.SuppressLint
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.data.WalletRepository
import com.example.mymoneyapp.wallet.db.Statement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatementPresenter(
    private var view: Wallet.View?,
    private var repository: WalletRepository
) : Wallet.Presenter {


    override fun deleteStatement(statementId: Int){
        repository.deleteStatement(statementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
    @SuppressLint("CheckResult")
    override fun findStatements(type: String?) {
        when (type) {
            null -> {
                repository.findAllStatement()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        this::onGetNotes,
                        this::onStatementFail
                    )
            }
            else -> {
                repository.findStatements(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        this::onGetNotes,
                        this::onStatementFail
                    )

            }
        }

    }

    override fun addStatement(statement: Statement) {
        repository.insertStatement(statement)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
    @SuppressLint("CheckResult")
    override fun findAccountBalance() {
        repository.accountBalance()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetAccountBalance,
                this::onStatementFail
            )
    }

    private fun onGetAccountBalance(d: Double?) {
        view?.showAccountBalance(d)
    }

    override fun onDestroy() {
        view = null
    }

    private fun onGetNotes(listOfStatemnt: List<Statement>) {
        view?.showStatement(listOfStatemnt)
    }


    private fun onStatementFail(t: Throwable) {
        view?.showFailure(t.message.toString())
    }

}