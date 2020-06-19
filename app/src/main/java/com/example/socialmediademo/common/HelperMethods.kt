package com.example.socialmediademo.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import kotlin.math.abs

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun Long.convertNumber():String {
    var numberString = ""
    numberString = when {
        abs(this / 1000000) > 1 -> {
            (this / 1000000).toString().toString() + "M"
        }
        abs(this / 1000) > 1 -> {
            (this / 1000).toString().toString() + "K"
        }
        else -> {
            this.toString()
        }
    }
    return numberString
}

fun TextView.setDataToTextView(data: String?, isForCount: Boolean=false, postFixString:String="") {
    if (data.isNullOrBlank().not()) {
        if (isForCount) {
            this.text = data?.toLong()?.convertNumber().plus(" $postFixString")
        }else{
            this.text = data
        }
    }else{
        this.visibility = View.GONE
    }
}

fun ImageView.setMediaImage(mediaUrl: String?, requestManager: RequestManager) {
    if (mediaUrl.isNullOrBlank().not()) {
        requestManager.load(mediaUrl).into(this)
    } else {
        this.visibility = View.GONE
    }
}