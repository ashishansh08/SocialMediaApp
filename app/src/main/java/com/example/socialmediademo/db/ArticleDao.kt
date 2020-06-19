package com.example.socialmediademo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleList:ArrayList<Articles>)

    @Query("SELECT * FROM articles")
    fun getArticleDataFromDb(): List<Articles>

    @Query("DELETE FROM articles where id>:id")
    fun deleteAllAfterId(id: Int)
}