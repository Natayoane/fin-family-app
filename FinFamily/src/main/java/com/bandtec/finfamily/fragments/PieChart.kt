package com.bandtec.finfamily.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.anychart.anychart.ValueDataEntry
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.fragment_pie_chart.*

class PieChart : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pie_chart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvAvaible.setOnClickListener {
            Toast.makeText(
                activity, """
            Trás seu saldo total!
            Calculo usado:
            Entradas - Saídas = Saldo
        """.trimIndent(), Toast.LENGTH_LONG
            ).show()
        }
        val entry = arguments?.getDouble("entry", 0.00)
        val expense = arguments?.getDouble("expense", 0.00)
        val avaible = entry!! - expense!!
        setupPieChart(entry, expense, avaible)

    }

    fun setupPieChart(entry: Double?, expense: Double?, avaible : Double) {
        val pie = AnyChart.pie()
        val data: List<DataEntry>
        data = listOf(
            ValueDataEntry("Entradas", entry),
            ValueDataEntry("Saídas", expense)
        )
        pie.setData(data)
        pie.setPalette(
            arrayOf(
                "#55910E",
                "#CC0000"
            )
        )

        pieChart.setChart(pie)
        pie.setInnerRadius("95%")

        if (avaible >= 0) {
            tvAvaible.setTextColor(Color.parseColor("#2176D3"))
        } else {
            tvAvaible.setTextColor(Color.parseColor("#CC0000"))
        }
        tvAvaible.text = String.format("%.2f", avaible)
    }

}
