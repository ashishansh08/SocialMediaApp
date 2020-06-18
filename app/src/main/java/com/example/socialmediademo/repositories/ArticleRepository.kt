package com.example.socialmediademo.repositories

import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.network.auth.ArticleApi
import io.reactivex.Single
import javax.inject.Inject

open class ArticleRepository @Inject constructor(var articleApi: ArticleApi): ArticleApi {

    override fun getArticle(id: Int, limit: Int): Single<ArrayList<Articles>> {
        return articleApi.getArticle(id, limit)
    }

}