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
    @SuppressLint("CheckResult")
    override fun findStatements() {
        repository.findStatements("earn")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetNotes,
                this::onStatementFail
            )

    }

    override fun addStatement( statement: Statement) {
        repository.insertStatement(statement)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onDestroy() {
        view = null
    }
    private fun onGetNotes(listOfStatemnt: List<Statement>){
        view?.showStatement(listOfStatemnt)
    }

    private fun onStatementFail(t: Throwable){
        view?.showFailure(t.message.toString())
    }

}