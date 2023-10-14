package com.example.mymoneyapp.wallet.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymoneyapp.R
import com.example.mymoneyapp.databinding.FragmentGraphicBinding
import org.eazegraph.lib.models.PieModel

class GraphicFragment : Fragment(R.layout.fragment_graphic) {
    private var binding: FragmentGraphicBinding? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGraphicBinding.bind(view)
        val earn =
            arguments?.getFloat(KEY_EARN) ?: throw IllegalArgumentException("email not found")
        val spend =
            arguments?.getFloat(KEY_SPEND) ?: throw IllegalArgumentException("email not found")

        binding?.includeCardAccountBalance?.tvValueEarnCardAccountBalance?.text = earn.toString()
        binding?.includeCardAccountBalance?.tvValueSpendCardAccountBalance?.text = spend.toString()

        val porc1 =  (spend * 100)/(earn)
        val porc2 = 100.0F - porc1
        binding?.includeCardGraphic?.piechart?.addPieSlice(
            PieModel(
                "Ganhos", porc2,
                resources.getColor(R.color.earn)
            )

        )
        binding?.includeCardGraphic?.piechart?.addPieSlice(
            PieModel(
                "Despesas", porc1,
                resources.getColor(R.color.spend)
            )

        )

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_EARN = "key_earn"
        const val KEY_SPEND = "key_spend"
    }

}