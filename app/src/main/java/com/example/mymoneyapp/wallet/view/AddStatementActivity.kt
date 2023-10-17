package com.example.mymoneyapp.wallet.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.CurrencyTextWatcher
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityAddStatementBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import com.example.mymoneyapp.wallet.presentation.StatementPresenter

class AddStatementActivity : AppCompatActivity(), Wallet.View {
    override lateinit var presenter: Wallet.Presenter
    lateinit var binding: ActivityAddStatementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStatementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etAddMoney.addTextChangedListener(watcher)

        presenter = StatementPresenter(view = this, viewHome = null,repository = DependencyInjector.walletRepository(this))
        binding.btnAddStatement.setOnClickListener {
            val money = binding.etAddMoney.text.toString().toDouble()
            val description = binding.etDescription.text.toString()
            val radioButtonId  = binding.radioGroupTypeWallet.checkedRadioButtonId
            val radioButton  : RadioButton = binding.root.findViewById<RadioButton>(radioButtonId)
            val type= autocompleteConvert(radioButton.text.toString())
            presenter.addStatement(Statement(type = type, money = money, description = description))
            finish()
        }



    }


    fun autocompleteConvert(s: String): String{
        return when (s) {
            getString(R.string.txtearn)-> "earn"
            getString(R.string.txtspend) -> "spend"
            else -> ""
        }
    }

    private val watcher = CurrencyTextWatcher()

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showFailure(message: String) {
    }

    override fun hideFailure() {
    }
}