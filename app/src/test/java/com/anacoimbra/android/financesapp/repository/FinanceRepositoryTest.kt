package com.anacoimbra.android.financesapp.repository

import com.anacoimbra.android.financesapp.datasource.FinanceDataSource
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.network.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class FinanceRepositoryTest {

    @MockK
    lateinit var dataSource: FinanceDataSource

    private lateinit var repository: FinanceRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        repository = FinanceRepository(dataSource)
    }

    @Test
    fun `when called getTransactions, then the repository should return data with Success`() {
        val transaction = mockk<Transaction>()
        val transactions = listOf(transaction, transaction)
        coEvery { dataSource.getTransactions() } returns transactions
        runBlockingTest {
            assertEquals(Resource.Success(transactions), repository.getTransactions())
        }
    }

    @Test
    fun `when called getTransactions, then the repository should return message with Error`() {
        coEvery { dataSource.getTransactions() } throws Exception("Unauthorized")
        runBlockingTest {
            assertEquals(
                Resource.Error<List<Transaction>>("Unauthorized"),
                repository.getTransactions()
            )
        }
    }

    @Test
    fun `when called registerExpense, then the repository should return data with Success`() {
        val response = mockk<Response>()
        coEvery { dataSource.registerExpense() } returns response
        runBlockingTest {
            assertEquals(Resource.Success(response), repository.registerExpense())
        }
    }

    @Test
    fun `when called registerExpense, then the repository should return message with Error`() {
        coEvery { dataSource.registerExpense() } throws Exception("Unauthorized")
        runBlockingTest {
            assertEquals(
                Resource.Error<Response>("Unauthorized"),
                repository.registerExpense()
            )
        }
    }

    @Test
    fun `when called registerIncome, then the repository should return data with Success`() {
        val response = mockk<Response>()
        coEvery { dataSource.registerIncome() } returns response
        runBlockingTest {
            assertEquals(Resource.Success(response), repository.registerIncome())
        }
    }

    @Test
    fun `when called registerIncome, then the repository should return message with Error`() {
        coEvery { dataSource.registerIncome() } throws Exception("Unauthorized")
        runBlockingTest {
            assertEquals(
                Resource.Error<Response>("Unauthorized"),
                repository.registerIncome()
            )
        }
    }
}