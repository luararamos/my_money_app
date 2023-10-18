package com.example.mymoneyapp.wallet.presentation

import android.annotation.SuppressLint
import com.example.mymoneyapp.wallet.RegisterUser
import com.example.mymoneyapp.wallet.data.WalletRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserPresenter(
    private var view: RegisterUser.View?,
    private var repository: WalletRepository
) : RegisterUser.Presenter {
    @SuppressLint("CheckResult")
    override fun addUser(user: com.example.mymoneyapp.wallet.db.User) {
        repository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    @SuppressLint("CheckResult")
    override fun updateUser(user: com.example.mymoneyapp.wallet.db.User) {
        repository.updateUser(com.example.mymoneyapp.wallet.db.User(id = 1, user.name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                findUsers()
            }
            .subscribe()
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

    private fun onGetUser(l: List<com.example.mymoneyapp.wallet.db.User>) {
        view?.showListUser(l)
    }

    @SuppressLint("CheckResult")
    private fun onUserFail(t: Throwable) {
        repository.insertUser(com.example.mymoneyapp.wallet.db.User(name = "MyMoney"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                findUsers()
            }
            .subscribe()
    }

    fun onDestroy() {
        view = null
    }




}