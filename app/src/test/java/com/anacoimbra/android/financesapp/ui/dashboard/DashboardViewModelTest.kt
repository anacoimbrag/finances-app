package com.anacoimbra.android.financesapp.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anacoimbra.android.financesapp.model.Transaction
import com.anacoimbra.android.financesapp.network.Resource
import com.anacoimbra.android.financesapp.repository.FinanceRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class DashboardViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: DashboardViewModel

    @MockK
    lateinit var repository: FinanceRepository

    private val observer: Observer<Resource<List<Transaction>>> = spyk(Observer { })

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getTransactions is called, then the observer is updated with success`() {
        val transaction = mockk<Transaction>()
        val success = Resource.Success(listOf(transaction, transaction))
        coEvery { repository.getTransactions() } returns success
        viewModel.transactions.observeForever(observer)
        coVerify { repository.getTransactions() }
        verify { observer.onChanged(success) }
    }

    @Test
    fun `when getTransactions is called, then the observer is updated with error`() {
        val error = Resource.Error<List<Transaction>>("Unauthorized")
        coEvery { repository.getTransactions() } returns error
        viewModel.transactions.observeForever(observer)
        coVerify { repository.getTransactions() }
        verify { observer.onChanged(error) }
    }
}