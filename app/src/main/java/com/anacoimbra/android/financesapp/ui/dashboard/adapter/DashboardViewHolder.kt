package com.anacoimbra.android.financesapp.ui.dashboard.adapter

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.android.financesapp.R
import com.anacoimbra.android.financesapp.helpers.toMoneyString
import com.anacoimbra.android.financesapp.model.Category
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.model.TransactionType
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.dashboard_header.view.*


open class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    init {
        setIsRecyclable(false)
    }

    open fun <T> bind(param: T) {}
}

class ChartViewHolder(view: View) : DashboardViewHolder(view) {

    override fun <T> bind(param: T) = with(itemView) {
        if (param is List<*>) {
            @Suppress("UNCHECKED_CAST")
            param as List<Transaction>
            val expenses = param.filter { it.type == TransactionType.EXPANSE }
            val incomes = param.filter { it.type == TransactionType.INCOME }
            val balance =
                incomes.sumByDouble { it.value ?: 0.0 } - expenses.sumByDouble { it.value ?: 0.0 }
            dashboardBalance.text = balance.toMoneyString()
            val expenseGroup = expenses.groupBy { it.category }
            val incomeGroup = incomes.groupBy { it.category }
            setupChart(expenseGroup, expensesChart)
            setupChart(incomeGroup, incomesChart, true)
        }
    }

    private fun setupChart(
        group: Map<Category?, List<Transaction>>,
        chart: PieChart,
        invertColor: Boolean = false
    ) {
        val entries = group.map { entry ->
            PieEntry(entry.value.size.toFloat(), entry.key?.name, entry.value)
        }
        val incomesDataSet = PieDataSet(entries, "")

        incomesDataSet.colors =
            if (!invertColor) ColorTemplate.JOYFUL_COLORS.toList() else ColorTemplate.JOYFUL_COLORS.reversed()

        chart.setUsePercentValues(true)

        val data = PieData(incomesDataSet)
        data.setDrawValues(false)
        chart.data = data

        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.form = Legend.LegendForm.CIRCLE
        l.textColor = ResourcesCompat.getColor(
            chart.resources,
            R.color.material_on_background_disabled,
            chart.context.theme
        )

        chart.description.isEnabled = false

        chart.animateY(600, Easing.EaseInOutQuad)
        chart.isHighlightPerTapEnabled = false
        chart.isRotationEnabled = false
        chart.setDrawEntryLabels(false)
        chart.transparentCircleRadius = 0f
        chart.invalidate()
    }
}