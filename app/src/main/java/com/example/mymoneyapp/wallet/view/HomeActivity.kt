package com.example.mymoneyapp.wallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityHomeBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import com.example.mymoneyapp.wallet.presentation.StatementPresenter

class HomeActivity : AppCompatActivity(), Wallet.HomeView, OnListClickListener {
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

        presenter = StatementPresenter(
            view = null,
            viewHome = this,
            repository = DependencyInjector.walletRepository(this)
        )
        presenter.findStatements()
        presenter.findUsers()


    }

    private fun setAlertDialog(statementId: Int) {
        AlertDialog.Builder(this)
            .setMessage(R.string.delete_message)
            .setNegativeButton(R.string.cancel) { dialog, which ->

            }
            .setPositiveButton(R.string.delete) { dialog, which ->
                presenter.deleteStatement(statementId)
            }
            .create()
            .show()
    }

    private fun setAlertDialNameUser() {
        AlertDialog.Builder(this)
        val input = EditText(this)
        input.hint = getString(R.string.user_name)
        input.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
            .setMessage(R.string.add_name_user)
            .setView(input)
            .setNegativeButton(R.string.cancel) { dialog, which ->

            }
            .setPositiveButton(R.string.save) { dialog, which ->
                val userName = input.text.toString()
                presenter.updateUser(User(name = userName))
            }
            .create()
            .show()
    }

    private fun setOnClicks() {
        binding.txtNameUser.setOnClickListener {
            setAlertDialNameUser()
        }
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
                        imgVisibility.setImageResource(R.drawable.ic_visibility_gone)

                    }

                    else -> {
                        presenter.findAccountBalance()
                        imgVisibility.setImageResource(R.drawable.ic_visibility)

                    }
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
        if (totalValue == 0.0) {
            binding.imgNotMoney.visibility = View.VISIBLE
            binding.imgNotMoney.background =
                ContextCompat.getDrawable(this, R.drawable.img_not_money);
        } else {
            binding.imgNotMoney.visibility = View.GONE
        }
        binding.textCvWallet.text = String.format("R$ %.2f", totalValue)
    }

    override fun showUser(name: String) {
        binding.txtNameUser.text = name
    }

    override fun showList(users: List<User>) {
        if (users.isEmpty()) {
            binding.txtNameUser.text = "MyMoneyApp"
            presenter.addUser(User(name = "MyMoneyApp"))
        } else {
            val user = users[0]
            binding.txtNameUser.text = user.name
        }

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
