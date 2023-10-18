package com.example.mymoneyapp.wallet.presentation

import android.annotation.SuppressLint
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.data.WalletRepository
import com.example.mymoneyapp.wallet.db.Statement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatementPresenter(
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
    override fun findValuesToGraphic() {
        repository.accountBalanceType("earn")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetGraphicEarn,
                this::onStatementFail
            )
    }

    // Success
    private fun onGetAccountBalance(d: Double) {
        if (d != 00.0) {
            viewHome?.hideFailure()
        }
        viewHome?.showAccountBalance(d)
    }

    private fun onGetListStatement(listOfStatemnt: List<Statement>) {
        viewHome?.hideProgress()
        viewHome?.showStatement(listOfStatemnt)
    }

    @SuppressLint("CheckResult")
    private fun onGetGraphicEarn(d: Double) {
        earn = d
        repository.accountBalanceType("spend")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::onGetGraphicSpend,
                this::onGraphicFail
            )
    }

    //Fail
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

    private fun onStatementFail(t: Throwable) {
        onGetAccountBalance(0.0)
//        viewHome?.showFailure(
//            Resources.getSystem().getString(R.string.txt_mensage_error_without_money)
//        )
    }

    private fun onGetGraphicSpend(d: Double) {
        spend = d
        viewHome?.showGraphic(earn, spend)
    }

    private fun onGraphicFail(t: Throwable) {
        if (earn != 00.0) spend = 00.0 else earn = 00.0
        viewHome?.showGraphic(earn, spend)
    }

    override fun onDestroy() {
        viewHome = null
    }

}