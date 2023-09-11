package com.example.mymoneyapp.presentation

import com.example.mymoneyapp.HomeActivity

class HomePresenter(private val view: HomeActivity){
    fun banana(): String{
        return "10.000,00"
    }
}