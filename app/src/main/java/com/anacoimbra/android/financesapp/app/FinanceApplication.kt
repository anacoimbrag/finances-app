package com.anacoimbra.android.financesapp.app

import android.app.Application
import com.anacoimbra.android.financesapp.network.networkModule
import com.anacoimbra.android.financesapp.repository.repositoryModule
import com.anacoimbra.android.financesapp.ui.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class FinanceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FinanceApplication)
            // use modules
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}