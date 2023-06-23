package com.example.mymoneyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var txtTotalValue = MutableLiveData<String>()
    private var vi = MutableLiveData<Boolean>()

    init {
        txtTotalValue.value = "10.000,00"
    }

    fun totalValue() : LiveData<String>{
        return txtTotalValue
    }
}