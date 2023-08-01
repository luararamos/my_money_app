package com.example.mymoneyapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoneyapp.presentation.MainPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)
        val txtWallet = findViewById<TextView>(R.id.text_cv_wallet)
        val imgWallet = findViewById<ImageView>(R.id.img_visibility)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        presenter = MainPresenter(this)

        imgWallet.setOnClickListener {
            if(txtWallet.text.toString() != "*****" ){
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
                val updateId = intent.extras?.getInt("updateId")
                if (updateId != null) {
                    dao.update(Statement(id = updateId, type = "win", money=100.00))
                } else {
                    dao.insert(Statement(type = "win", money = 100.00))
                }

                runOnUiThread {
                    Log.i("Teste", "Salvou")
                }
            }.start()
        }





        val rv = findViewById<RecyclerView>(R.id.rv_items_list)
        rv.adapter = ListAdapter()
        rv.layoutManager = LinearLayoutManager(this)
    }
}