package com.example.mymoneyapp.wallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityHomeBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import com.example.mymoneyapp.wallet.presentation.StatementPresenter
import com.example.mymoneyapp.wallet.view.GraphicFragment.Companion.KEY_EARN
import com.example.mymoneyapp.wallet.view.GraphicFragment.Companion.KEY_SPEND

class HomeActivity : AppCompatActivity(), Wallet.HomeView, OnListClickListener {
    override lateinit var presenter: Wallet.Presenter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)

        val fragment = StatementFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.home_fragment, fragment)
            commit()
        }
        setOnClicks()
        presenter = StatementPresenter(
            view = null,
            viewHome = this,
            repository = DependencyInjector.walletRepository(this)
        )
        presenter.findStatements()
        presenter.findUsers()
        presenter.findAccountBalance()


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

                R.id.graphic -> {

                    goToGraphicScreen(70.0F, 50.0F)
                    true
                }

                else -> true
            }
        }
    }



    override fun onRestart() {
        super.onRestart()
        presenter.findStatements()
        presenter.findAccountBalance()
    }

    override fun showStatement(response: List<Statement>) {

        val fragment = StatementFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_fragment, fragment)
            commit()
        }
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.arrayListLiveData.postValue(response)
    }

    override fun showAccountBalance(totalValue: Double?) {
        binding.cardvisibilityWallet.setText(String.format("R$ %.2f", totalValue))
    }

    override fun showGraphic(earnValue: Double, spendValue: Double) {

        val fragment = GraphicFragment().apply {
            arguments = Bundle().apply {
                putFloat(KEY_EARN, earnValue.toFloat())
                putFloat(KEY_SPEND, spendValue.toFloat())
            }

        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_fragment, fragment)
            commit()
        }
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
        binding.prograssbarHomeFragment.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.prograssbarHomeFragment.visibility = View.GONE
    }

    override fun showFailure(message: String) {
        when(message){
            getString(R.string.txt_mensage_error_without_money) ->{
                binding.imgErrorActivityHome.setImageDrawable(getDrawable(R.drawable.img_not_money))
            }
            getString(R.string.txt_mensage_error_occurred) -> {
                binding.imgErrorActivityHome.setImageDrawable(getDrawable(R.drawable.img_error_occurred))
            }

        }
    }

    override fun hideFailure() {
        binding.imgErrorActivityHome.visibility = View.GONE
    }

    override fun onClickDelete(id: Int, type: String) {
        setAlertDialog(id)
    }


    fun goToGraphicScreen(earn: Float, spend: Float) {
        presenter.findValuesToGraphic()
    }

}

