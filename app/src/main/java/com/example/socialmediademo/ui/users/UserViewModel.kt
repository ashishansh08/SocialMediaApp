package com.example.socialmediademo.ui.users

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediademo.models.Users
import com.example.socialmediademo.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository):ViewModel() {

    private var disposable: Disposable?= null
    var mutableUsersList : MutableLiveData<ArrayList<Users>>? = MutableLiveData()


    @SuppressLint("CheckResult")
    fun getUsers(page:Int, limit:Int){
        disposable = userRepository.getUsers(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                mutableUsersList?.value = data
            }, { t: Throwable? ->
                // for now i have used UnknownHostException to show offline list
                // enhancement : we can add interceptors with retrofit to check the same
                if (t is UnknownHostException){
                    // mutableList?.value = Articles()
                }
            })
    }

    fun deleteAllAfterId(id:Int){
        userRepository.deleteUserAfterId(id)
    }

    fun insertUser(usersList:ArrayList<Users>){
        userRepository.insertUser(usersList)
    }

    fun getUsersFromDb(): List<Users> {
        return userRepository.getUsersFromDb()
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