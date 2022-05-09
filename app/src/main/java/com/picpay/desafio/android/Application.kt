package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.KEY
import com.picpay.desafio.android.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(appModules)
            properties(mapOf(KEY to "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"))

        }
    }
}