package com.br.lojasurfart

import android.app.Application
import com.br.lojasurfart.di.koin.modules.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    companion object {
        var BASE_URL = "https://surf-art-development.herokuapp.com/api/"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(provideDependency())
        }
    }

    private fun provideDependency() = appComponent
}