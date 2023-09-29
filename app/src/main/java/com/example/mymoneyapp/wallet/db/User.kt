package com.example.mymoneyapp.wallet.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class User(
    @PrimaryKey (autoGenerate = true)var id: Int = 0,
    @ColumnInfo(name = "name", defaultValue = "MyMom") var name: String
)
