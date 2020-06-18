package com.example.socialmediademo.di

import com.example.socialmediademo.MainActivity
import com.example.socialmediademo.di.article.ArticleModule
import com.example.socialmediademo.di.article.ArticleScope
import com.example.socialmediademo.di.article.MainViewModelsModule
import com.example.socialmediademo.di.article.MainFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ArticleScope
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, ArticleModule::class, MainFragmentBuilderModule::class]) //It means that only AuthActivity.class can use AuthViewModelsModule.class
    abstract fun provideAuthActivity(): MainActivity

}