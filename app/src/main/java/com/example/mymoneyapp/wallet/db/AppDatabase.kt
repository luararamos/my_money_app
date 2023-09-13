package com.example.mymoneyapp.wallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Statement::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun statementDao(): StatementDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "my_money"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else{
                INSTANCE as AppDatabase
            }
        }

        fun getInstanceWithoutContext(): AppDatabase{
            return INSTANCE!!
        }
    }
}