package com.anacoimbra.android.financesapp.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.android.financesapp.R
import com.anacoimbra.android.financesapp.helpers.toMoneyString
import com.anacoimbra.android.financesapp.helpers.toShortDate
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.model.TransactionType
import com.anacoimbra.android.timeline.enums.LineVisibility
import kotlinx.android.synthetic.main.transaction_item.view.*

class DashboardAdapter : RecyclerView.Adapter<DashboardViewHolder>() {

    var transactions: List<Transaction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder =
        if (viewType == 2)
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.transaction_item, parent, false)
            )
        else ChartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_header, parent, false)
        )

    override fun getItemCount(): Int = transactions.size + 1

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == 1)
            holder.bind(transactions)
        if (type == 2)
            holder.bind(transactions[position - 1])
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) 1 else 2

    inner class ViewHolder(view: View) : DashboardViewHolder(view) {
        override fun <T> bind(param: T) = with(itemView) {
            if (param is Transaction) {
                param.category?.icon?.let {
                    if (it > 0)
                        transactionTypeIndicator.setIcon(it)
                }
                transactionTypeIndicator.setBulletBackgroundDrawable(if (param.type == TransactionType.INCOME) R.drawable.income_background else R.drawable.expense_background)
                transactionTypeIndicator.setIconTint(if (param.type == TransactionType.INCOME) R.color.income_color else R.color.expense_color)
                if (adapterPosition == 1)
                    transactionTypeIndicator.lineVisibility = LineVisibility.BOTTOM
                if (adapterPosition == itemCount - 1)
                    transactionTypeIndicator.lineVisibility = LineVisibility.TOP
                transactionCategory.text = param.category?.name
                transactionDescription.text = param.description
                transactionDate.text = param.date.toShortDate()
                transactionValue.text = param.value.toMoneyString()
            }
        }
    }
}