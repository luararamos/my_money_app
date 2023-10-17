package com.example.mymoneyapp.wallet

import com.example.mymoneyapp.wallet.db.User

interface RegisterUser {
    interface Presenter{
        fun addUser(user: User)
        fun updateUser(user: User)
        fun findUsers()
    }

    interface View  {
        fun showUser(name: String)
        fun showListUser(users: List<User>)
    }
}