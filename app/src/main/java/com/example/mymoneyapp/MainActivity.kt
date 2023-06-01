package com.example.mymoneyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txtWallet = findViewById<TextView>(R.id.txt_wallet)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.totalValue().observe(this, Observer {
            txtWallet.text = it
        })

        val rv = findViewById<RecyclerView>(R.id.rv_items_list)
        rv.adapter = ListAdapter()
        rv.layoutManager = LinearLayoutManager(this)
    }
}