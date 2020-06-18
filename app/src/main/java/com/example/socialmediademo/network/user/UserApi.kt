package com.example.socialmediademo.network.user

import com.example.socialmediademo.models.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/users")
    fun getUsers(@Query("page") id:Int=1,
                   @Query("limit") limit:Int=10
    ): Single<ArrayList<Users>>

}