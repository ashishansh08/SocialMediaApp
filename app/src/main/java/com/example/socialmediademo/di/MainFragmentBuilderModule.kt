package com.example.socialmediademo.di

import com.example.socialmediademo.ui.article.ArticleFragment
import com.example.socialmediademo.ui.user_details.UserDetailsFragment
import com.example.socialmediademo.ui.users.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun getArticleScreen():ArticleFragment

    @ContributesAndroidInjector
    abstract fun getUserScreen():UserFragment


    @ContributesAndroidInjector
    abstract fun getNotificationScreen():UserDetailsFragment

}