package com.example.mymoneyapp.wallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityHomeBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.presentation.StatementPresenter

class HomeActivity : AppCompatActivity(), Wallet.View, OnListClickListener {
    override lateinit var presenter: Wallet.Presenter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)

        setRecyclerView()
        setCardVisibility()
        setOnClicks()

        presenter = StatementPresenter(this, DependencyInjector.walletRepository(this))
        presenter.findStatements()
        presenter.findAccountBalance()


    }

    private fun setAlertDialog(statementId: Int) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(R.string.cancel) {  dialog, which ->

            }
            .setPositiveButton(R.string.delete){  dialog, which ->
                presenter.deleteStatement(statementId)
            }
            .create()
            .show()
    }

    private fun setOnClicks() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddStatementActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavAppBar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    presenter.findStatements()
                    true
                }

                R.id.earn -> {
                    presenter.findStatements("earn")
                    true
                }

                R.id.spend -> {
                    presenter.findStatements("spend")
                    true
                }

                else -> true
            }

        }

    }

    private fun setCardVisibility() {
        with(binding) {
            imgVisibility.setOnClickListener {
                when {
                    textCvWallet.text.toString() != "*****" -> {
                        textCvWallet.text = "*****"
                        imgVisibility.setImageResource(R.drawable.ic_visibility)
                    }

                    else -> imgVisibility.setImageResource(R.drawable.ic_visibility_gone)
                }
            }
        }
    }

    private fun setRecyclerView() {
        val result = mutableListOf<Statement>()
        adapter = ListAdapter(result, this)
        binding.rvItemsList.adapter = adapter
        binding.rvItemsList.layoutManager = LinearLayoutManager(this)
    }

    override fun onRestart() {
        super.onRestart()
        presenter.findStatements()
        presenter.findAccountBalance()
    }

    override fun showStatement(response: List<Statement>) {
        binding.rvItemsList.adapter = ListAdapter(response, this)
    }

    override fun showAccountBalance(totalValue: Double?) {
        binding.textCvWallet.text = totalValue.toString()
    }


    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showFailure(message: String) {
    }

    override fun onClickDelete(id: Int, type: String) {
        setAlertDialog(id)
    }
}
