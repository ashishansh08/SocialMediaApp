package com.example.socialmediademo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.socialmediademo.common.MediaConvertor
import com.example.socialmediademo.common.UserConvertor
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Articles")
data class Articles (

    @PrimaryKey
    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("comments")
    val comments: String? = null,

    @SerializedName("likes")
    val likes: String? = null,

    @SerializedName("media")
    @TypeConverters(MediaConvertor::class)
    val media: ArrayList<Media>? = null,

    @SerializedName("user")
    @TypeConverters(UserConvertor::class)
    val user: ArrayList<User>? = null

):Parcelable