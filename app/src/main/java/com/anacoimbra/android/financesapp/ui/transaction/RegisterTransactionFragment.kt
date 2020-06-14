package com.anacoimbra.android.financesapp.ui.transaction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.anacoimbra.android.financesapp.R
import com.anacoimbra.android.financesapp.helpers.*
import com.anacoimbra.android.financesapp.model.TransactionType
import com.anacoimbra.android.financesapp.network.Resource
import com.anacoimbra.android.financesapp.ui.transaction.adapter.CategoryAdapter
import com.anacoimbra.android.financesapp.ui.transaction.dialog.DatePickerFragment
import kotlinx.android.synthetic.main.register_transaction_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RegisterTransactionFragment : Fragment(), DatePickerFragment.OnDateSelectedListener {

    private val viewModel: TransactionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_transaction_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerTransactionAmountInput.setMoneyMask()

        registerTransactionDateInput.setMask(defaultDatePattern)
        registerTransactionDate.setEndIconOnClickListener {
            DatePickerFragment()
                .show(childFragmentManager, "date-picker")
        }

        btnCreateTransaction.setOnClickListener {
            Log.d("RegisterTransaction", "Transaction {")
            Log.d("RegisterTransaction", "  type: ${getTransactionType()},")
            Log.d(
                "RegisterTransaction",
                "  value: ${registerTransactionAmountInput.getMoneyValue()},"
            )
            Log.d("RegisterTransaction", "  category: ${getTransactionCategory()},")
            Log.d(
                "RegisterTransaction",
                "  description: ${registerTransactionDescriptionInput.text},"
            )
            Log.d("RegisterTransaction", "  date: ${registerTransactionDateInput.getDate()}")
            Log.d("RegisterTransaction", "}")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> registerTransactionCategoryList.isEnabled = false
                is Resource.Success -> {
                    val items = it.data.orEmpty()
                    val adapter = CategoryAdapter(registerTransactionCategoryList)
                    registerTransactionCategoryList.setAdapter(adapter)
                    adapter.setItems(items)
                    registerTransactionCategoryList.isEnabled = true
                }
            }
        })
    }

    override fun onDateSelected(date: Date) {
        registerTransactionDateInput.setText(date.toShortDate())
    }

    private fun getTransactionType() =
        when (transactionType.checkedButtonId) {
            R.id.expense -> TransactionType.EXPANSE
            R.id.income -> TransactionType.INCOME
            else -> throw IllegalStateException("Please select a transaction type")
        }

    @Suppress("UNCHECKED_CAST")
    private fun getTransactionCategory() =
        (registerTransactionCategoryList.adapter as? CategoryAdapter)?.getSelected()
}