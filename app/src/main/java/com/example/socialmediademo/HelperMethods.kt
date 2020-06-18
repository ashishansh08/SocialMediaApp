package com.example.socialmediademo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import kotlin.math.abs

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