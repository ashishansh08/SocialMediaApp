package com.example.socialmediademo.models

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Media (
    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("blogId")
    val blogId: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null
):Parcelable