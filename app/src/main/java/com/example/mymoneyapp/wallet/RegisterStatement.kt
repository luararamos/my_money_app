package com.example.mymoneyapp.wallet

import com.example.mymoneyapp.common.BasePresenter
import com.example.mymoneyapp.common.BaseView
import com.example.mymoneyapp.wallet.db.Statement

interface RegisterStatement {

    interface Presenter : BasePresenter {
        fun addStatement(statement: Statement)

    }

    interface View : BaseView<Presenter> {
        fun showProgress()
        fun hideProgress()
        fun showFailure(message: String)
        fun hideFailure()
    }

}