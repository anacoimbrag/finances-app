package com.anacoimbra.android.financesapp.repository

import com.anacoimbra.android.financesapp.datasource.FinanceDataSource
import com.anacoimbra.android.financesapp.model.Category
import com.anacoimbra.android.financesapp.network.Resource

class CategoryRepository(private val dataSource: FinanceDataSource) {

    suspend fun getCategories(): Resource<List<Category>> =
        try {
            val finances = dataSource.getCategories()
            Resource.Success(finances)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
}