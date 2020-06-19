package com.example.socialmediademo.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import dagger.android.support.DaggerFragment


open class BaseFragment: DaggerFragment() {
 /*   var networkReceiver:NetworkReceiver= NetworkReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkReceiver, intentFilter)
    }

    override fun onDestroy() {
        requireActivity().unregisterReceiver(networkReceiver);
        super.onDestroy()
}
*/

    inner class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isInternetChanged()
        }
    }

    open fun isInternetChanged(){
    }
}