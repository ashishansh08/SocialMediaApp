package com.example.socialmediademo.common

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.socialmediademo.models.Media
import com.example.socialmediademo.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MediaConvertor {

    @TypeConverter
    fun  fromPost(value: ArrayList<Media>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Media>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPost(value: String): ArrayList<Media>? {
        if (!TextUtils.isEmpty(value)){
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Media>>() {}.type
            return gson.fromJson(value, type)
        }
        return null
    }

}