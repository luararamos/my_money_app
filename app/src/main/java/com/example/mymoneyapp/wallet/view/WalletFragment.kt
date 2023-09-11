package com.example.mymoneyapp.wallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoneyapp.R

class WalletFragment: Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view =  inflater.inflate(R.layout.fragment_wallet, container, false)
        val rv = view?.findViewById<RecyclerView>(R.id.rv_items_list)
        rv?.adapter = ListAdapter()
        rv?.layoutManager = LinearLayoutManager(context)
        return view
    }
}