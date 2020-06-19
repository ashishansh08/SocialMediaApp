package com.example.socialmediademo.di.article

import com.example.socialmediademo.db.ArticleDao
import com.example.socialmediademo.di.MainScope
import com.example.socialmediademo.network.auth.ArticleApi
import com.example.socialmediademo.repositories.ArticleRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ArticleModule {

    companion object{
        @MainScope
        @Provides
        fun provideArticleApi(retrofit: Retrofit): ArticleApi {
            return retrofit.create(ArticleApi::class.java)
        }

        @MainScope
        @Provides
        fun provideRepository(articleApi: ArticleApi, articleDao: ArticleDao): ArticleRepository {
            return ArticleRepository(articleApi, articleDao)
        }
    }

}