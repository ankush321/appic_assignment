package com.appic.assignment.di

import com.appic.assignment.data.repository.DataRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repoModule = module {
    single { DataRepository(androidApplication())  }
}