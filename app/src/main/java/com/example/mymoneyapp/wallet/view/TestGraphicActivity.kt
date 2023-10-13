package com.example.mymoneyapp.wallet.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoneyapp.databinding.ActivityTestGraphicBinding
import org.eazegraph.lib.models.PieModel


class TestGraphicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestGraphicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestGraphicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set the percentage of language used
//        binding.tvR.setText(Integer.toString(40))
//        binding.tvPython.setText(Integer.toString(30))

        // Set the data and color to the pie chart
        // Set the data and color to the pie chart
//        binding.piechart.addPieSlice(
//            PieModel(
//                "R", binding.tvR.getText().toString().toFloat(),
//                Color.parseColor("#FFA726")
//            )
//        )
//        binding.piechart.addPieSlice(
//            PieModel(
//                "Python", binding.tvPython.getText().toString().toFloat(),
//                Color.parseColor("#66BB6A")
//            )
//        )
    }
}