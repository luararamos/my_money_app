package com.example.mymoneyapp.wallet.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityHomeBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.presentation.HomePresenter
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.App

class HomeActivity : AppCompatActivity(), Wallet.View {
    override lateinit var presenter: Wallet.Presenter
    lateinit var binding: ActivityHomeBinding
    lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)

        val result = mutableListOf<Statement>()
        adapter = ListAdapter(result)
        binding.rvItemsList.adapter = adapter
        binding.rvItemsList.layoutManager =LinearLayoutManager(this)

        presenter = HomePresenter(this, DependencyInjector.walletRepository(this))

        with(binding){
            imgVisibility.setOnClickListener {
                when{
                    textCvWallet.text.toString() != "*****" ->{
                        textCvWallet.text = "*****"
                        imgVisibility.setImageResource(R.drawable.ic_visibility)
                    }
                    else -> imgVisibility.setImageResource(R.drawable.ic_visibility_gone)
                }

            }
        }


        binding.fab.setOnClickListener {
            presenter.addStatement(Statement(type = "earn", money = 100.50, description = "Video"))
//            Thread {
//                val app = application as App
//                val dao = app.db.statementDao()
//                dao.insert(Statement(type = "earn", money = 100.50, description = "Vídeo"))
////                dao.insert(Statement(type = "spend", money = 100.50, description = "Pizza"))
//
//
//                runOnUiThread {
//                    Log.i("Teste", "Salvou")
//                }
//            }.start()
        }

        presenter.findStatements()


    }
    override fun showStatement(response: List<Statement>) {
        binding.rvItemsList.adapter = ListAdapter(response)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showFailure(message: String) {
    }
}
