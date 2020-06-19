package com.example.socialmediademo.ui.article

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediademo.models.Articles
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.repositories.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class ArticleViewModel @Inject constructor(var articleRepository: ArticleRepository):ViewModel() {

    private var disposable: Disposable?= null
    var mutableList : MutableLiveData<ArrayList<Articles>>? = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getArticle(page:Int, limit:Int){
        disposable = articleRepository.getArticle(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                mutableList?.value = data
            }, { t: Throwable? ->
                //TODO Error can be handled here.
            })
    }

    fun deleteAllAfterId(id:Int){
        articleRepository.deleteArticlesAfterId(id)
    }

    fun insertArticles(articlesList:ArrayList<Articles>){
        articleRepository.insertArticles(articlesList)
    }

    fun getArticlesFromDb(): List<Articles> {
        return articleRepository.getArticlesFromDb()
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