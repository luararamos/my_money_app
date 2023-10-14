package com.example.mymoneyapp.wallet.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mymoneyapp.wallet.db.Statement

class MainViewModel: ViewModel() {
    val arrayListLiveData = MutableLiveData<List<Statement>>()
}