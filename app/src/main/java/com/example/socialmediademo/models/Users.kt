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
    var id:Int = 0,

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    var createdAt: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    var avatar:String? = null,

    @ColumnInfo(name = "lastname")
    @SerializedName("lastname")
    var lastname: String? = null,

    @ColumnInfo(name ="city")
    @SerializedName("city")
    var city: String? = null,

    @ColumnInfo(name ="designation")
    @SerializedName("designation")
    var designation: String? = null,

    @ColumnInfo(name="about")
    @SerializedName("about")
    var about: String? = null

):Parcelable