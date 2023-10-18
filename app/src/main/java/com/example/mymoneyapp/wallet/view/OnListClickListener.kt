package com.example.mymoneyapp.wallet.view

import com.example.mymoneyapp.wallet.db.Statement

interface OnListClickListener {
    fun onClickDelete(statement: Statement)
}