package com.example.socialmediademo.di

import android.app.Application
import com.example.socialmediademo.BaseApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                      ActivityBuilderModule::class,
                      AppModule::class,
                      ViewModelFactoryModule::class
])

interface AppComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun applicationBuilder(app: Application): Builder
        fun build(): AppComponent
    }

}