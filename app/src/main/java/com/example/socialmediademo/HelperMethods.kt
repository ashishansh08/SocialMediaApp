package com.example.socialmediademo

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