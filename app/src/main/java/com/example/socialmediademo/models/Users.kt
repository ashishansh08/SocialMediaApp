package com.example.socialmediademo.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Users")
data class Users (

    @PrimaryKey
    @SerializedName("id")
    val id:Int = 0,

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar:String? = null,

    @ColumnInfo(name = "lastname")
    @SerializedName("lastname")
    val lastname: String? = null,

    @ColumnInfo(name ="city")
    @SerializedName("city")
    val city: String? = null,

    @ColumnInfo(name ="designation")
    @SerializedName("designation")
    val designation: String? = null,

    @ColumnInfo(name="about")
    @SerializedName("about")
    val about: String? = null

):Parcelable