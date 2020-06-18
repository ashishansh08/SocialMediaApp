package com.example.socialmediademo.di.article

import androidx.lifecycle.ViewModel
import com.example.socialmediademo.di.ViewModelKey
import com.example.socialmediademo.ui.article.ArticleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

    @Module
    abstract class ArticleViewModelsModule {

        @Binds
        @IntoMap
        @ViewModelKey(ArticleViewModel::class)
        abstract fun bindAuthViewModel(viewModel: ArticleViewModel?): ViewModel

}