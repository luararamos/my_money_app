package com.example.mymoneyapp.wallet.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityAddStatementBinding
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.presentation.StatementPresenter

class AddStatementActivity : AppCompatActivity(), Wallet.View {
    override lateinit var presenter: Wallet.Presenter
    lateinit var binding: ActivityAddStatementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStatementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.statement_type)
        binding.autoType.setText(items.first())

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.autoType.setAdapter(adapter)


        presenter = StatementPresenter(this, DependencyInjector.walletRepository(this))
        binding.btnAddStatement.setOnClickListener {
            val money = binding.etAddMoney.text.toDouble()
            val description = binding.etDescription.text.toString()
            presenter.addStatement(Statement(type = "earn", money = money, description = description))
        }

    }

    fun autocompleteConvert(s: String): String{
        return when (s) {
            "Ativo"-> "earn"
            "Passivo" -> ""
            else -> ""
        }
    }

    override fun showStatement(response: List<Statement>) {
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showFailure(message: String) {
    }
}