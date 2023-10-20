package com.example.mymoneyapp.wallet.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoneyapp.R
import com.example.mymoneyapp.databinding.FragmentStatementBinding
import com.example.mymoneyapp.wallet.db.Statement


class StatementsFragment : Fragment(R.layout.fragment_statement), OnListClickListener {
    private var binding: FragmentStatementBinding? = null
    private lateinit var adapter: ListAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatementBinding.bind(view)

        activity?.let {
            mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        }


        mainViewModel.arrayListLiveData.observe(viewLifecycleOwner) { arrayList ->
            setRecyclerView(arrayList)

        }

    }

    private fun setRecyclerView(list: List<Statement>) {
        adapter = ListAdapter(list, this)
        binding?.rvItemsList?.adapter = adapter
        binding?.rvItemsList?.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun setAlertDialog(statement: Statement) {
        AlertDialog.Builder(context)
            .setMessage(R.string.delete_message)
            .setNegativeButton(R.string.cancel) { dialog, which ->

            }
            .setPositiveButton(R.string.delete) { dialog, which ->
                mainViewModel.selectItem(statement)
            }
            .create()
            .show()
    }

    override fun onClickDelete(statement: Statement) {
        setAlertDialog(statement)
    }



}