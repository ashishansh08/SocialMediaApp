package com.example.socialmediademo.models

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("blogId")
    val blogId: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("avatar")
    val avatar: String? = null,

    @SerializedName("lastname")
    val lastname: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("designation")
    val designation: String? = null,

    @SerializedName("about")
    val about: String? = null

)