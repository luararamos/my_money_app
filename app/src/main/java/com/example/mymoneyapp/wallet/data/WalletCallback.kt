package com.example.mymoneyapp.wallet.data

interface WalletCallback {
    fun onSuccess( response: List<String>)
    fun onError(response: String)
    fun onComplete()
}