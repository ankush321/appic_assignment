package com.appic.assignment

import android.app.Application
import com.appic.assignment.di.repoModule
import com.appic.assignment.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(repoModule, viewModule))
        }
    }
}