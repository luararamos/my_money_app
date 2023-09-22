package com.example.mymoneyapp.wallet.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymoneyapp.R
import com.example.mymoneyapp.wallet.db.Statement

class ListAdapter(
    private val list: List<Statement>,
    private val listener: OnListClickListener?
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bills, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val itemCurrent = list[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(item : Statement) {
            val textDescription: TextView = itemView.findViewById(R.id.txt_description_bill)
            val textValue: TextView = itemView.findViewById(R.id.txt_money)
            val imgTypeStatement: ImageView = itemView.findViewById(R.id.img_type_statement)

            textDescription.text = item.description
            val doubleNum = item.money
            textValue.text = String.format("R$ %.2f", doubleNum)
            when(item.type){
                "earn" -> imgTypeStatement.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_earn))
                "spend" -> imgTypeStatement.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_spend))
                else -> imgTypeStatement.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_add))
            }

            itemView.setOnClickListener {
                listener?.onClickDelete(item.id, item.type)
            }



        }
    }
}