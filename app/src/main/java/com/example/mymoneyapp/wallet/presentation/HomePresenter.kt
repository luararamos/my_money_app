package com.example.mymoneyapp.wallet.presentation

import com.example.mymoneyapp.wallet.view.HomeActivity

class HomePresenter(private val view: HomeActivity){
    fun banana(): String{
        return "10.000,00"
    }
}