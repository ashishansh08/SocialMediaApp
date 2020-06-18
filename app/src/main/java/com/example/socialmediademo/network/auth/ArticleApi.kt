package com.example.socialmediademo.network.auth

import com.example.socialmediademo.models.Articles
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    
    @GET("/blogs")
    fun getArticle(@Query("page") id:Int=1,
                   @Query("limit") limit:Int=10
                   ): Single<ArrayList<Articles>>

}