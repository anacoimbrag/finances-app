package com.anacoimbra.android.financesapp.datasource

import com.anacoimbra.android.financesapp.model.Category
import com.anacoimbra.android.financesapp.model.Transaction
import okhttp3.Response
import retrofit2.http.GET

interface FinanceDataSource {

    @GET("53b99dcbde58329c9da63b58ea722659/raw/06eaeb2931cd9e460d6935699e2fbe87dc6ebf31/finances.json")
    suspend fun getTransactions(): List<Transaction>

    suspend fun registerExpense(): Response

    suspend fun registerIncome(): Response

    @GET("15dbfc4717e89db98878906f1cd5dbea/raw/80a0b9d96c864dfb7a3cfc1773b2b6e5683e6d36/categories.json")
    suspend fun getCategories(): List<Category>
}