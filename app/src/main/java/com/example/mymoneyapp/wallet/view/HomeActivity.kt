package com.example.mymoneyapp.wallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymoneyapp.R
import com.example.mymoneyapp.common.DependencyInjector
import com.example.mymoneyapp.databinding.ActivityHomeBinding
import com.example.mymoneyapp.wallet.RegisterUser
import com.example.mymoneyapp.wallet.Wallet
import com.example.mymoneyapp.wallet.db.Statement
import com.example.mymoneyapp.wallet.db.User
import com.example.mymoneyapp.wallet.presentation.StatementPresenter
import com.example.mymoneyapp.wallet.presentation.UserPresenter
import com.example.mymoneyapp.wallet.view.GraphicFragment.Companion.KEY_EARN
import com.example.mymoneyapp.wallet.view.GraphicFragment.Companion.KEY_SPEND
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeActivity : AppCompatActivity(), Wallet.HomeView, RegisterUser.View {
    override lateinit var presenter: Wallet.Presenter
    private lateinit var presenterUser: RegisterUser.Presenter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.md_theme_dark_background)

        var fragment = StatementsFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.home_fragment_activity_home, fragment)
            commit()
        }
        setOnClicks()
        presenter = StatementPresenter(
            viewHome = this,
            repository = DependencyInjector.walletRepository(this)
        )
        presenterUser = UserPresenter(
            view = this,
            repository = DependencyInjector.walletRepository(this)
        )
        presenter.findStatements()
        presenterUser.findUsers()
        presenter.findAccountBalance()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        hideWithAppBarLayout(binding.cardvisibilityActivityHome, binding.txtNameUser, binding.homeFragmentActivityHome, binding.fabActivityHome,binding.appBarActivityHome)

        mainViewModel.selectedItem.observe(this, Observer {statement ->
            presenter.deleteStatement(statement.id)

        })

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
                presenterUser.updateUser(User(name = userName))
            }
            .create()
            .show()
    }

    private fun setOnClicks() {
        binding.txtNameUser.setOnClickListener {
            setAlertDialNameUser()
        }
        binding.fabActivityHome.setOnClickListener {
            val intent = Intent(this, AddStatementActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNavAppBarActivityHome.setOnMenuItemClickListener { item ->

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

        val fragment = StatementsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_fragment_activity_home, fragment)
            commit()
        }

        mainViewModel.arrayListLiveData.postValue(response)
    }

    override fun showAccountBalance(totalValue: Double?) {
        binding.cardvisibilityActivityHome.setText(String.format("R$ %.2f", totalValue))
        binding.cardvisibilityActivityHome.showVisibility(false)
    }

    override fun showGraphic(earnValue: Double, spendValue: Double) {

        val fragment = GraphicFragment().apply {
            arguments = Bundle().apply {
                putFloat(KEY_EARN, earnValue.toFloat())
                putFloat(KEY_SPEND, spendValue.toFloat())
            }

        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_fragment_activity_home, fragment)
            commit()
        }
    }

    override fun showUser(name: String) {
        binding.txtNameUser.text = name
    }

    override fun showListUser(users: List<User>) {
        if (users.isEmpty()) {
            binding.txtNameUser.text = "MyMoneyApp"
            presenterUser.addUser(User(name = "MyMoneyApp"))
        } else {
            val user = users[0]
            binding.txtNameUser.text = user.name
        }

    }

    override fun showProgress() {
        binding.progressbarActivityHome.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressbarActivityHome.visibility = View.GONE
    }

    override fun showFailure(message: String) {
        when (message) {
            getString(R.string.txt_mensage_error_without_money) -> {
                binding.imgErrorActivityHome.setImageDrawable(getDrawable(R.drawable.img_not_money))
            }

            getString(R.string.txt_mensage_error_occurred) -> {
                binding.imgErrorActivityHome.setImageDrawable(getDrawable(R.drawable.img_error_occurred))
            }
        }
        binding.imgErrorActivityHome.visibility = View.VISIBLE
    }

    override fun hideFailure() {
        binding.imgErrorActivityHome.visibility = View.GONE
    }

    private fun goToGraphicScreen(earn: Float, spend: Float) {
        presenter.findValuesToGraphic()
    }

    @BindingAdapter("hideWithAppBarLayout")
    fun hideWithAppBarLayout(view: View, textView: TextView,  frame : FrameLayout, fab: FloatingActionButton ,appBarLayout: AppBarLayout?) {
        val params = frame.layoutParams as ViewGroup.MarginLayoutParams
        if (appBarLayout == null) return
        if (view.parent is CoordinatorLayout) {
            val lp = view.layoutParams as CoordinatorLayout.LayoutParams
            lp.anchorId = appBarLayout.id
            appBarLayout.addOnOffsetChangedListener(BaseOnOffsetChangedListener { appBar: AppBarLayout, verticalOffset: Int ->
                val percentage =
                    Math.abs(verticalOffset).toFloat() / appBar.totalScrollRange
                //on collapsing
                if (percentage == 1f) {
                    view.visibility = View.GONE
                    textView.visibility = View.GONE
                    fab.visibility = View.GONE
                    params.topMargin = 8
                    frame.layoutParams = params
                }
                //on expending
                if (percentage == 0f){
                    view.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                    params.topMargin = 102
                    frame.layoutParams = params

                }
            } as BaseOnOffsetChangedListener<*>)
        }
    }

}

