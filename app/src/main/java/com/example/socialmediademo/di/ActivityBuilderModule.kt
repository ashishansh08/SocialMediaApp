package com.example.socialmediademo.di

import com.example.socialmediademo.MainActivity
import com.example.socialmediademo.di.auth.MainModule
import com.example.socialmediademo.di.auth.MainScope
import com.example.socialmediademo.di.auth.MainViewModelsModule
import com.example.socialmediademo.di.auth.MainFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, MainModule::class, MainFragmentBuilderModule::class]) //It means that only AuthActivity.class can use AuthViewModelsModule.class
    abstract fun provideAuthActivity(): MainActivity

}