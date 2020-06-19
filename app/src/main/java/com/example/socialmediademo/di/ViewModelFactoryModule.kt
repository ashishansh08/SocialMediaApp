package com.example.socialmediademo.di

import androidx.lifecycle.ViewModelProvider
import com.example.socialmediademo.common.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

//This module we will provide in AppComponent because all viewModels will use viewModel factory
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}