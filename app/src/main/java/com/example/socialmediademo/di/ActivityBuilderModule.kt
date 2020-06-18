package com.example.socialmediademo.di

import com.example.socialmediademo.MainActivity
import com.example.socialmediademo.di.article.ArticleModule
import com.example.socialmediademo.di.article.ArticleScope
import com.example.socialmediademo.di.article.ArticleViewModelsModule
import com.example.socialmediademo.di.user.UserModule
import com.example.socialmediademo.di.user.UserViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [ArticleViewModelsModule::class, ArticleModule::class,
        MainFragmentBuilderModule::class, UserModule::class, UserViewModelsModule::class])
    abstract fun provideAuthActivity(): MainActivity

}