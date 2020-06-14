package com.anacoimbra.android.financesapp.repository

import com.anacoimbra.android.financesapp.datasource.FinanceDataSource
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.network.Resource
import okhttp3.Response

class FinanceRepository(private val dataSource: FinanceDataSource) {

    suspend fun getTransactions(): Resource<List<Transaction>> =
        try {
            val finances = dataSource.getTransactions()
            Resource.Success(finances)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }

    suspend fun registerExpense() =
        try {
            val response = dataSource.registerExpense()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error<Response>(e.localizedMessage)
        }

    suspend fun registerIncome() =
        try {
            val response = dataSource.registerIncome()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error<Response>(e.localizedMessage)
        }
}