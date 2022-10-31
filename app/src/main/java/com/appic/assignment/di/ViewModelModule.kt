package com.appic.assignment.di

import com.appic.assignment.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        MainViewModel(dataRepository = get())
    }
}