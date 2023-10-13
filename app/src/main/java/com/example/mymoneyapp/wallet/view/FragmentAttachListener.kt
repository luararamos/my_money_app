package com.example.mymoneyapp.wallet.view

import com.example.mymoneyapp.wallet.db.Statement

interface FragmentAttachListener {
    fun goToListStatementScreen(list: List<Statement>)
}