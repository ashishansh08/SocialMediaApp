package com.example.socialmediademo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.socialmediademo.models.Users

@Database(
        entities = [Users::class],
        version = 1
)

abstract class UserDataBase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
}