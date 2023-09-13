package com.example.mymoneyapp.wallet.data

interface WalletDataSource {

    fun statement(type: String?)
}