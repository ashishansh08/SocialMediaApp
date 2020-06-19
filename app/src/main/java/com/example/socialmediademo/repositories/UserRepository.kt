package com.example.socialmediademo.repositories

import com.example.socialmediademo.db.UserDao
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.network.user.UserApi
import io.reactivex.Single
import javax.inject.Inject

open class UserRepository @Inject constructor(var userApi: UserApi, var userDao:UserDao): UserApi {

    override fun getUsers(id: Int, limit: Int): Single<ArrayList<Users>> {
        return userApi.getUsers(id, limit)
    }

    fun deleteUserAfterId(id: Int){
        userDao.deleteAllAfterId(id)
    }

    fun insertUser(usersList: ArrayList<Users>) {
        userDao.insertUser(usersList)
    }

    fun getUsersFromDb(): List<Users> {
        return userDao.getUserDataFromDb()
    }


}