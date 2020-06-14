package com.anacoimbra.android.financesapp.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { FinanceRepository(get()) }
    single { CategoryRepository(get()) }
}
