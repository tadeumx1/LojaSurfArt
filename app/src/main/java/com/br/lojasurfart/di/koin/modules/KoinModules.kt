package com.br.lojasurfart.di.koin.modules

import com.br.lojasurfart.BaseApplication
import com.br.lojasurfart.repositories.ProductRepository
import com.br.lojasurfart.repositories.ProductService
import com.br.lojasurfart.viewmodels.HomeViewModel
import com.br.lojasurfart.util.SharedPreferencesUtil
import com.br.lojasurfart.viewmodels.CategoryViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
}

val repositoryModule = module {
    factory { providesIoDispatcher() }
    single { ProductRepository(get(), get()) }
    single { SharedPreferencesUtil(androidContext()) }
}

val uiModule = module {
    // factory { RecyclerAdapterProduct(androidContext()) }
}

val networkModule = module {

    single { provideDefaultOkhttpClient() }
    single { provideRetrofit(get()) }
    single { provideUserService(get()) }

}

fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BaseApplication.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideUserService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

val appComponent = listOf(viewModelModule, repositoryModule, uiModule, networkModule)

const val DEFAULT_NAMESPACE = "default"