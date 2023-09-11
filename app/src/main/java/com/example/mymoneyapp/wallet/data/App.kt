package com.example.mymoneyapp

import android.app.Application

class App : Application() {
    lateinit var db : AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}