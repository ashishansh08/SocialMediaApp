package com.example.socialmediademo.di.article

import com.example.socialmediademo.ui.article.ArticleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun getArticleScreen():ArticleFragment

}