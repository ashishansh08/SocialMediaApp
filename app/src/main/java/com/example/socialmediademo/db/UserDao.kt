package com.example.socialmediademo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmediademo.models.Users

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userList:ArrayList<Users>)

    @Query("SELECT * FROM users")
    fun getUserDataFromDb(): List<Users>

    @Query("DELETE FROM users where id>:id")
    fun deleteAllAfterId(id: Int)
}