package com.example.socialmediademo.di.article

import com.example.socialmediademo.network.auth.ArticleApi
import com.example.socialmediademo.repositories.ArticleRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ArticleModule {

    companion object{
        @ArticleScope
        @Provides
        fun provideArticleApi(retrofit: Retrofit): ArticleApi {
            return retrofit.create(ArticleApi::class.java)
        }

        @ArticleScope
        @Provides
        fun provideRepository(articleApi: ArticleApi): ArticleRepository {
            return ArticleRepository(articleApi)
        }
    }

}