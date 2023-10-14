package com.example.mymoneyapp.wallet.presentation

import android.annotation.SuppressLint
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.data.WalletRepository
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatementPresenter(
    private var view: Wallet.View?,
    private var viewHome: Wallet.HomeView?,
    private var repository: WalletRepository
) : Wallet.Presenter {

    private var earn: Double = 0.0
    private var spend: Double = 0.0

    @SuppressLint("CheckResult")
    override fun findAccountBalance() {
        repository.accountBalance()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetAccountBalance,
                this::onAccountBalanceFail
            )
    }
    @SuppressLint("CheckResult")
    override fun findAccountType() {
        repository.accountBalanceType("earn")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetGraphicEarn,
                this::onStatementFail
            )

    }


    @SuppressLint("CheckResult")
    override fun findUsers() {
        repository.findAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetUser,
                this::onUserFail
            )
    }

    @SuppressLint("CheckResult")
    override fun findStatements(type: String?) {
        when (type) {
            null -> {
                repository.findAllStatement()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        this::onGetListStatement,
                        this::onStatementFail
                    )
            }

            else -> {
                repository.findStatements(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        this::onGetListStatement,
                        this::onStatementFail
                    )
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun addStatement(statement: Statement) {
        repository.insertStatement(statement)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    @SuppressLint("CheckResult")
    override fun addUser(user: User) {
        repository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


    @SuppressLint("CheckResult")
    override fun updateUser(user: User) {
        repository.updateUser(User(id = 1, user.name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                findUsers()
            }
            .subscribe()
    }

    @SuppressLint("CheckResult")
    override fun deleteStatement(statementId: Int) {
        repository.deleteStatement(statementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                findStatements()
                findAccountBalance()
            }
            .subscribe()
    }

    @SuppressLint("CheckResult")
    private fun onGetGraphicEarn(d: Double) {
        earn = d
        repository.accountBalanceType("spend")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetGraphicSpend,
                this::onStatementFail
            )
    }
    @SuppressLint("CheckResult")
    private fun onGetGraphicSpend(d: Double) {
        spend = d
        viewHome?.showGraphic(earn, spend)

    }
    private fun onGetAccountBalance(d: Double) {
        viewHome?.showAccountBalance(d)
    }

    private fun onGetUser(l: List<User>) {
        viewHome?.showList(l)
    }

    private fun onGetListStatement(listOfStatemnt: List<Statement>) {
        viewHome?.showStatement(listOfStatemnt)
    }

    @SuppressLint("CheckResult")
    private fun onAccountBalanceFail(t: Throwable) {
        repository.accountBalanceType("earn")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetAccountBalance,
                this::onAccountBalanceFailEarn
            )


    }

    @SuppressLint("CheckResult")
    private fun onAccountBalanceFailEarn(t: Throwable) {
        repository.accountBalanceType("spend")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetAccountBalance,
                this::onStatementFail
            )


    }

    private fun onUsertTast() {
        viewHome?.showUser("Falha ao salvar o nome")
    }

    @SuppressLint("CheckResult")
    private fun onUserFail(t: Throwable) {
        repository.insertUser(User(name = "MyMoney"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun onStatementFail(t: Throwable) {
        onGetAccountBalance(0.0)
    }

    override fun onDestroy() {
        view = null
    }

}