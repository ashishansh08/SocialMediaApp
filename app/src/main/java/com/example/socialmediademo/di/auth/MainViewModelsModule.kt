package com.example.socialmediademo.di.auth

import androidx.lifecycle.ViewModel
import com.example.socialmediademo.di.ViewModelKey
import com.example.socialmediademo.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

    @Module
    abstract class MainViewModelsModule {

        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindAuthViewModel(viewModel: HomeViewModel?): ViewModel

}