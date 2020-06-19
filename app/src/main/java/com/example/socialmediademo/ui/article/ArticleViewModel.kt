package com.example.socialmediademo.ui.article

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.repositories.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class ArticleViewModel @Inject constructor(var authRepository: ArticleRepository):ViewModel() {

    private var disposable: Disposable?= null
    var mutableList : MutableLiveData<ArrayList<Articles>>? = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getArticle(page:Int, limit:Int){
        disposable = authRepository.getArticle(page, limit)
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

    /**
     * Need to dispose the subscription
     * when view model is no more
     */
    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}