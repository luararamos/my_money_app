package com.example.mymoneyapp.wallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Statement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "money") val money: Double,
    @ColumnInfo(name = "created_date") val createdDate: Date = Date(),
)