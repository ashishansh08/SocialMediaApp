package com.example.socialmediademo.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.socialmediademo.common.AppConstants
import com.example.socialmediademo.R
import com.example.socialmediademo.db.ArticleDao
import com.example.socialmediademo.db.UserDao
import com.example.socialmediademo.db.UserDataBase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        @Singleton
        @Provides
        fun provideName(): String {
            return "Ashish"
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        @Singleton
        @Provides
        fun provideRequestOptions(): RequestOptions {
            return RequestOptions
                    .placeholderOf(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
        }

        @Singleton
        @Provides
        fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
            return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
        }


        @Singleton
        @Provides
        fun providesAppDatabase(context: Application): UserDataBase = Room.databaseBuilder(context, UserDataBase::class.java, "user_db").allowMainThreadQueries().build()

        @Singleton
        @Provides
        fun provideVehicleDao(basDatabase: UserDataBase): UserDao = basDatabase.getUserDao()

        @Singleton
        @Provides
        fun provideUserDao(basDatabase: UserDataBase): ArticleDao = basDatabase.getArticleDao()
    }


}