/*
 * Created by Dmitry Lipski on 05.01.21 12:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 12:32
 */

package com.lipssoftware.manchester.united.utils

const val MAN_UTD_ID = 33
const val PREMIER_LEAGUE_ID = 39
const val SEASON = 2020

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, message = null)
    }
}