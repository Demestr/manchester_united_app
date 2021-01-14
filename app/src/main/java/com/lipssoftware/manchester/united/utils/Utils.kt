/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.01.21 12:45
 */

package com.lipssoftware.manchester.united.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

const val MAN_UTD_ID = 33
const val PREMIER_LEAGUE_ID = 39
const val SEASON = 2020
const val DATE_PATTERN_IN = "EEE, d MMM yyyy HH:mm:ss z"
const val DATE_PATTERN_OUT = "EEE, d MMM yyyy HH:mm:ss"

@Keep
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun convertStringToDate(date: String): Long {
    return SimpleDateFormat(DATE_PATTERN_IN, Locale.UK).parse(date).time
}

fun convertDateToString(date: Long): String {
    return SimpleDateFormat(DATE_PATTERN_OUT, Locale.UK).format(date)
}

fun getTextFromHtml(html: String, flag: Int = Html.FROM_HTML_MODE_COMPACT): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(html, flag)
    else
        Html.fromHtml(html)
}