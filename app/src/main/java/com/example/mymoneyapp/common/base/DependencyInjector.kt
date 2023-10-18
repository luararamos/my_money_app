package com.example.mymoneyapp.common

import android.content.Context
import com.example.mymoneyapp.wallet.data.WalletRepository

object DependencyInjector {
    fun walletRepository(context: Context): WalletRepository{
        return WalletRepository(context)
    }
}