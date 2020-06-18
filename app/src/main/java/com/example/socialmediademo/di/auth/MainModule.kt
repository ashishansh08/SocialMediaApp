package com.example.socialmediademo.di.auth

import com.example.socialmediademo.network.auth.ArticleApi
import com.example.socialmediademo.repositories.ArticleRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    companion object{
        @MainScope
        @Provides
        fun provideAuthApi(retrofit: Retrofit): ArticleApi {
            return retrofit.create(ArticleApi::class.java)
        }

        @MainScope
        @Provides
        fun provideRepository(articleApi: ArticleApi): ArticleRepository {
            return ArticleRepository(articleApi)
        }
    }

}