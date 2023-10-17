package com.example.mymoneyapp.wallet

import com.example.mymoneyapp.common.BasePresenter
import com.example.mymoneyapp.common.BaseView
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User

interface Wallet {

    // camada presenter
    interface Presenter : BasePresenter {
        fun findStatements(type:String? = null)
        fun findAccountBalance()
        fun findValuesToGraphic()
        fun deleteStatement(statementId: Int)

    }

    // camada de view
    interface HomeView : BaseView<Presenter> {
        fun showProgress()
        fun hideProgress()
        fun showFailure(message: String)
        fun hideFailure()
        fun showStatement(response: List<Statement>)
        fun showAccountBalance(totalValue: Double?)
        fun showGraphic(earnValue: Double, spendValue: Double)

    }

}