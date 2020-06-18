package com.example.socialmediademo.repositories

import com.example.socialmediademo.models.Users
import com.example.socialmediademo.network.user.UserApi
import io.reactivex.Single
import javax.inject.Inject

open class UserRepository @Inject constructor(var userApi: UserApi): UserApi {

    override fun getUsers(id: Int, limit: Int): Single<ArrayList<Users>> {
        return userApi.getUsers(id, limit)
    }


}