package com.example.emmanueldaviest.internal.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emmanueldaviest.presentation.AppViewModelFactory
import com.example.emmanueldaviest.presentation.listView.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}