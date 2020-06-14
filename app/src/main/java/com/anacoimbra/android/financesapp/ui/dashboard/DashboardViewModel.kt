package com.anacoimbra.android.financesapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.anacoimbra.android.financesapp.helpers.emitResource
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.network.Resource
import com.anacoimbra.android.financesapp.repository.FinanceRepository
import kotlinx.coroutines.Dispatchers

class DashboardViewModel(private val repository: FinanceRepository) : ViewModel() {

    val transactions: LiveData<Resource<List<Transaction>>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitResource(repository.getTransactions())
        }
}