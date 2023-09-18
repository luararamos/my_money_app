package com.example.mymoneyapp.wallet

import com.example.mymoneyapp.common.BasePresenter
import com.example.mymoneyapp.common.BaseView
import com.example.mymoneyapp.wallet.db.Statement

interface Wallet {

    // camada presenter
    interface Presenter : BasePresenter {
        fun findStatements(type:String? = null)
        fun addStatement(statement: Statement)
    }

    // camada de view
    interface View : BaseView<Presenter> {
        fun showStatement(response: List<Statement>)
        fun showProgress()
        fun hideProgress()
        fun showFailure(message: String)
    }

}