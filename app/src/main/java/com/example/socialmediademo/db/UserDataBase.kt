package com.example.socialmediademo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.socialmediademo.common.MediaConvertor
import com.example.socialmediademo.common.UserConvertor
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users

@Database(entities = [Users::class, Articles::class],
        version = 1
)
@TypeConverters(MediaConvertor::class, UserConvertor::class)
abstract class UserDataBase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getArticleDao(): ArticleDao
}