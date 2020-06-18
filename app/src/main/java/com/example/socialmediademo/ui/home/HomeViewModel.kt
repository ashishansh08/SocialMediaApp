package com.example.socialmediademo.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.repositories.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(var authRepository: ArticleRepository):ViewModel() {

    var mutableList : MutableLiveData<ArrayList<Articles>>? = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getUser(userId:Int){
        authRepository.getArticle(userId, 10)
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