package com.example.mymoneyapp.wallet.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Completable

    @Update
    fun updateUser(user: User): Completable

    @Query("SELECT * FROM User")
    fun getAllUser(): Single<List<User>>

//    @Query("SELECT * FROM User WHERE id = '0'")
//    fun getUser(): Single<String>
}