package com.anacoimbra.android.financesapp.network

import com.anacoimbra.android.financesapp.BuildConfig
import com.anacoimbra.android.financesapp.datasource.FinanceDataSource
import com.anacoimbra.android.financesapp.helpers.CategoryDeserializer
import com.anacoimbra.android.financesapp.model.Category
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideDataSource() }
}

fun provideDataSource(): FinanceDataSource {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(FinanceDataSource::class.java)
}

private val gson =
    GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .setPrettyPrinting()
        .registerTypeAdapter(Category::class.java, CategoryDeserializer())
        .create()

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()
}