/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 11:36
 */

package com.lipssoftware.manchester.united.utils

import android.text.Html
import androidx.annotation.Keep

const val MAN_UTD_ID = 33
const val PREMIER_LEAGUE_ID = 39
const val SEASON = 2020

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

fun getTextFromHtml(html: String, flag: Int = Html.FROM_HTML_MODE_COMPACT): String {
    val text =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            Html.fromHtml(html, flag)
        else
            Html.fromHtml(html)
    return text.toString()
}