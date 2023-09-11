package com.example.mymoneyapp.wallet.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoneyapp.R
import com.example.mymoneyapp.wallet.presentation.HomePresenter
import com.example.mymoneyapp.wallet.data.App
import com.example.mymoneyapp.wallet.data.Statement
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private lateinit var presenter: HomePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)
        val txtWallet = findViewById<TextView>(R.id.text_cv_wallet)
        val imgWallet = findViewById<ImageView>(R.id.img_visibility)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        presenter = HomePresenter(this)

        imgWallet.setOnClickListener {
            if (txtWallet.text.toString() != "*****") {
                txtWallet.text = "*****"
                imgWallet.setImageResource(R.drawable.ic_visibility)
            } else {
                txtWallet.text = presenter.banana()
                imgWallet.setImageResource(R.drawable.ic_visibility_gone)

            }
        }

        fab.setOnClickListener {
            Thread {
                val app = application as App
                val dao = app.db.statementDao()
                dao.insert(Statement(type = "win", money = 100.50))


                runOnUiThread {
                    Log.i("Teste", "Salvou")
                }
            }.start()
        }



    }
}