package com.example.mymoneyapp.presentation

import com.example.mymoneyapp.MainActivity

class MainPresenter(private val view: MainActivity){
    fun banana(): String{
        return "10.000,00"
    }
}