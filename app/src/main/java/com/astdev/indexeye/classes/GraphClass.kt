package com.astdev.indexeye.classes

import android.annotation.SuppressLint
import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.Collections
import java.util.concurrent.atomic.AtomicReference

class GraphClass {

    companion object{

        @SuppressLint("ResourceAsColor")
        fun graphHebdo(barChart: BarChart) {
            val yData = floatArrayOf(28f, 25f, 30f, 23f, 31f, 34f, 10f)
            val jours = arrayOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim")
            val xEntry = AtomicReference(ArrayList<String>())
            val yEntry = ArrayList<BarEntry>()
            for (i in yData.indices) yEntry.add(BarEntry(i.toFloat(), yData[i]))
            Collections.addAll(xEntry.get(), *jours)
            val set1 = BarDataSet(yEntry, "")
            set1.color = Color.parseColor("#FF1497CF")
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.axisRight.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.description.isEnabled = false
            barChart.setTouchEnabled(false)
            barChart.setFitBars(true)
            barChart.xAxis.granularity = 1f
            barChart.xAxis.isGranularityEnabled = true
            barChart.xAxis.setDrawGridLines(false)
            val xAxis = barChart.xAxis
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return jours[value.toInt()]
                }
            }
            barChart.data = data
            barChart.animateY(900)
        }



        fun graphMensuel(barChart: BarChart) {
            val yData = floatArrayOf(54f, 90f, 120f, 10f, 40f, 100f, 60f, 75f, 80f, 20f, 30f, 80f)
            val mois = arrayOf(
                "Janv", "Févr", "Mars", "Avr", "Mai", "Juin", "Juil",
                "Août", "Sept", "Oct", "Nov", "Déc"
            )
            val xEntry = AtomicReference(ArrayList<String>())
            val yEntry = ArrayList<BarEntry>()
            for (i in yData.indices)
                yEntry.add(BarEntry(i.toFloat(), yData[i]))
            Collections.addAll(xEntry.get(), *mois)
            val set1 = BarDataSet(yEntry, "")
            set1.color = Color.parseColor("#FF1497CF")
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.axisRight.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.description.isEnabled = false
            barChart.setTouchEnabled(false)
            barChart.setFitBars(true)
            barChart.xAxis.granularity = 1f
            barChart.xAxis.isGranularityEnabled = true
            barChart.xAxis.setDrawGridLines(false)
            val xAxis = barChart.xAxis
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return mois[value.toInt()]
                }
            }
            barChart.data = data
            barChart.animateY(900)
        }

        fun graphAnnuel(barChart: BarChart) {
            val yData = floatArrayOf(235f, 200f, 120f, 190f)
            val an = arrayOf("2019", "2020", "2021", "2022")
            val xEntry = AtomicReference(ArrayList<String>())
            val yEntry = ArrayList<BarEntry>()
            for (i in yData.indices) yEntry.add(BarEntry(i.toFloat(), yData[i]))
            Collections.addAll(xEntry.get(), *an)
            val set1 = BarDataSet(yEntry, "")
            set1.color = Color.parseColor("#FF1497CF")
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.axisRight.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.description.isEnabled = false
            barChart.setTouchEnabled(false)
            barChart.setFitBars(true)
            barChart.xAxis.granularity = 1f
            barChart.xAxis.isGranularityEnabled = true
            barChart.xAxis.setDrawGridLines(false)
            val xAxis = barChart.xAxis
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return an[value.toInt()]
                }
            }
            barChart.data = data
            barChart.animateY(900)
        }

    }
}