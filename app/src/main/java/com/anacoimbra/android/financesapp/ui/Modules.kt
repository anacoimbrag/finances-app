package com.anacoimbra.android.financesapp.ui

import com.anacoimbra.android.financesapp.ui.dashboard.DashboardViewModel
import com.anacoimbra.android.financesapp.ui.reports.ReportsViewModel
import com.anacoimbra.android.financesapp.ui.profile.ProfileViewModel
import com.anacoimbra.android.financesapp.ui.transaction.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { TransactionViewModel(get()) }
    viewModel { ReportsViewModel() }
    viewModel { ProfileViewModel() }
}