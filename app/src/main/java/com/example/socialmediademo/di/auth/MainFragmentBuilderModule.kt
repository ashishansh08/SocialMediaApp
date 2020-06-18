package com.example.socialmediademo.di.auth

import com.example.socialmediademo.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun getHomeScreen():HomeFragment

}