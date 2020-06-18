package com.example.socialmediademo.di.user

import androidx.lifecycle.ViewModel
import com.example.socialmediademo.di.ViewModelKey
import com.example.socialmediademo.ui.article.ArticleViewModel
import com.example.socialmediademo.ui.users.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

    @Module
    abstract class UserViewModelsModule {

        @Binds
        @IntoMap
        @ViewModelKey(UserViewModel::class)
        abstract fun bindUserViewModel(viewModel: UserViewModel?): ViewModel

}