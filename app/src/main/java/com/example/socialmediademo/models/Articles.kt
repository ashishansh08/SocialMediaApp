package com.example.socialmediademo.models

import com.google.gson.annotations.SerializedName

data class Articles (
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
    val media: ArrayList<Media>? = null,

    @SerializedName("user")
    val user: ArrayList<User>? = null
)