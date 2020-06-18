package com.example.socialmediademo.di.user

import com.example.socialmediademo.di.MainScope
import com.example.socialmediademo.network.user.UserApi
import com.example.socialmediademo.repositories.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UserModule {

    companion object{
        @MainScope
        @Provides
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }

        @MainScope
        @Provides
        fun provideRepository(userApi: UserApi): UserRepository {
            return UserRepository(userApi)
        }
    }
}