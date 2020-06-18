package com.example.socialmediademo.ui.users

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.repositories.ArticleRepository
import com.example.socialmediademo.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository):ViewModel() {

    var mutableList : MutableLiveData<ArrayList<Users>>? = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getUsers(page:Int, limit:Int){
        userRepository.getUsers(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                mutableList?.value = data
            }, { t: Throwable? ->

                // for now i have used UnknownHostException to show offline list
                // enhancement : we can add interceptors with retrofit to check the same
                if (t is UnknownHostException){
                    // mutableList?.value = Articles()
                }
            })
    }
}