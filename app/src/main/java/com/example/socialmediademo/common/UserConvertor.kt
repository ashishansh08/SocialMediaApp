package com.example.socialmediademo.common

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.socialmediademo.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConvertor {

    @TypeConverter
    fun  fromPost(value: ArrayList<User>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<User>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPost(value: String): ArrayList<User>? {
        if (!TextUtils.isEmpty(value)){
            val gson = Gson()
            val type = object : TypeToken<ArrayList<User>>() {}.type
            return gson.fromJson(value, type)
        }
        return null
    }

}