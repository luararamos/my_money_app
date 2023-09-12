package com.example.mymoneyapp.wallet.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymoneyapp.R

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bills, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        return holder.bind()
    }

    override fun getItemCount(): Int {
        return 35
    }

    inner class ListViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind() {


        }
    }
}