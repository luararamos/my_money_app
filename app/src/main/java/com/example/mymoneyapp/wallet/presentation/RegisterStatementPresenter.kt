package com.example.mymoneyapp.wallet.presentation

import android.annotation.SuppressLint
import com.example.mymoneyapp.wallet.RegisterStatement
import com.example.mymoneyapp.wallet.data.WalletRepository
import com.example.mymoneyapp.wallet.db.Statement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterStatementPresenter(
    private var view: RegisterStatement.View?,
    private var repository: WalletRepository
) : RegisterStatement.Presenter {
    @SuppressLint("CheckResult")
    override fun addStatement(statement: Statement) {
        repository.insertStatement(statement)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    override fun onDestroy() {
        view = null
    }
}